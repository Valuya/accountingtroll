package be.valuya.accountingtroll;

import be.valuya.accountingtroll.domain.ATAccount;
import be.valuya.accountingtroll.domain.ATBookPeriod;
import be.valuya.accountingtroll.domain.ATBookYear;
import be.valuya.accountingtroll.domain.ATPeriodType;
import be.valuya.bob.core.BobFileConfiguration;
import be.valuya.bob.core.api.troll.MemoryCachingBobAccountingManager;
import be.valuya.winbooks.api.accountingtroll.WinbooksTrollAccountingManager;
import be.valuya.winbooks.api.extra.WinbooksFileConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(JUnit4.class)
public class AccountingTrollImplementationComparisonTest {

    private Map<String, AccountingManager> accountingManagers = new HashMap<>();

    @Before
    public void init() {
        String bobTestFolderString = System.getProperty("bob.test.folder");
        Path bobTestPath = Paths.get(bobTestFolderString);
        BobFileConfiguration bobFileConfiguration = new BobFileConfiguration(bobTestPath);
        MemoryCachingBobAccountingManager bobAccountingManager = new MemoryCachingBobAccountingManager(bobFileConfiguration);
        accountingManagers.put("bob", bobAccountingManager);

        String winbooksFolderString = System.getProperty("winbooks.test.folder");
        Path winbooksTestPath = Paths.get(winbooksFolderString);
        WinbooksFileConfiguration winbooksFileConfiguration = new WinbooksFileConfiguration();
        winbooksFileConfiguration.setBaseFolderPath(winbooksTestPath);
        winbooksFileConfiguration.setBaseName("DCTLOISELE");
        winbooksFileConfiguration.setReadTablesToMemory(true);
        winbooksFileConfiguration.setResolveCaseInsensitiveSiblings(true);
        winbooksFileConfiguration.setResolveArchivePaths(false);
        winbooksFileConfiguration.setIgnoreMissingArchives(true);
        WinbooksTrollAccountingManager winbooksAccountingManager = new WinbooksTrollAccountingManager(winbooksFileConfiguration);
        accountingManagers.put("winbooks", winbooksAccountingManager);
    }

    @Test
    public void testBookYears() {
        System.out.println("");
        System.out.println("streamBookYears");
        Function<AccountingManager, Stream<ATBookYear>> streamFunction = a -> a.streamBookYears()
                .sorted(Comparator.comparing(ATBookYear::getStartDate))
                .filter(b -> isComparableYear(b.getStartDate().getYear()));
        this.testImplementations(streamFunction);
    }


    @Test
    public void testBookPeriods() {
        System.out.println("");
        System.out.println("streamBookYears");
        Function<AccountingManager, Stream<ATBookPeriod>> streamFunction = a -> a.streamPeriods()
                .sorted(Comparator.comparing(ATBookPeriod::getStartDate).thenComparing(ATBookPeriod::getEndDate))
                .filter(b -> isComparableYear(b.getBookYear().getStartDate().getYear()))
                .filter(b -> isComparablePeriodType(b.getPeriodType()));

        this.testImplementations(streamFunction);
    }


    @Test
    public void testAccounts() {
        System.out.println("");
        System.out.println("streamAccounts");
        Function<AccountingManager, Stream<ATAccount>> streamFunction = a -> a.streamAccounts()
                .sorted(Comparator.comparing(ATAccount::getCode))
                .filter(b -> this.isCOmparableAccountCode(b.getCode()));

        this.testImplementations(streamFunction);
    }


    private <T> void testImplementations(Function<AccountingManager, Stream<T>> testFunction) {
        Map<String, List<T>> implementationsResults = accountingManagers.entrySet()
                .stream()
                .sequential()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> collectStream(e.getValue(), testFunction)
                ));
        IntSummaryStatistics countStatistics = implementationsResults.values()
                .stream()
                .sequential()
                .mapToInt(List::size)
                .summaryStatistics();
        int minCount = countStatistics.getMin();
        int maxCount = countStatistics.getMax();

        boolean allPass = true;
        if (minCount != maxCount) {
            System.err.println("Implemtation result count differs : from " + minCount + " to " + maxCount + " results");
            allPass = false;
        }
        for (int i = 0; i < minCount; i++) {
            final int index = i;
            Map<String, T> implementationsSingleResult = implementationsResults.entrySet().stream()
                    .sequential()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> e.getValue().get(index)
                    ));
            T curValue = null;
            String curValueImplName = null;
            boolean pass = true;

            for (String key : implementationsSingleResult.keySet()) {
                T implementationValue = implementationsSingleResult.get(key);
                if (curValue == null) {
                    curValue = implementationValue;
                    curValueImplName = key;
                } else {
                    if (!valueEqual(curValue, implementationValue)) {
                        System.err.println(index + ": Implementation value " + key + " differs with " + curValueImplName);
                        System.out.println(index + ": " + key + " value: ");
                        System.out.println(index + ": " + implementationValue);
                        System.out.println(index + ": " + curValueImplName + " value: ");
                        System.out.println(index + ": " + curValue);
                        pass = false;
                    }
                }
            }
            if (pass) {
                System.out.println(index + ": " + curValue);
            } else {
                allPass = false;
            }
        }

        Assert.assertTrue(allPass);
    }

    private <T> boolean valueEqual(T valA, T valB) {
        if (valA instanceof ATBookYear) {
            return bookYearEqual((ATBookYear) valA, (ATBookYear) valB);
        } else if (valA instanceof ATBookPeriod) {
            return bookPeriodsEqual((ATBookPeriod) valA, (ATBookPeriod) valB);
        } else if (valA instanceof ATAccount) {
            return accountsEqual((ATAccount) valA, (ATAccount) valB);
        }
        return false;
    }

    private boolean accountsEqual(ATAccount valA, ATAccount valB) {
        // Analytics not set in bob // FIXME
        // Labels may differ
        // Currency not set in bob, but well in winnbooks for bank accounts (550000 for DL)
        return valA.getCode().equals(valB.getCode())
                && valA.isYearlyBalanceReset() == valB.isYearlyBalanceReset();
    }

    private boolean bookPeriodsEqual(ATBookPeriod valA, ATBookPeriod valB) {
        return valA.getPeriodType().equals(valB.getPeriodType())
                && bookYearEqual(valA.getBookYear(), valB.getBookYear())
                && valA.getStartDate().equals(valB.getStartDate())
                && valA.getEndDate().equals(valB.getEndDate());
    }

    private boolean bookYearEqual(ATBookYear bookYearA, ATBookYear bookYearB) {
//        return bookYearA.equals(bookYearB);
        return bookYearA.getEndDate().equals(bookYearB.getEndDate())
                && bookYearA.getStartDate().equals(bookYearB.getStartDate());
    }

    private <T> List<T> collectStream(AccountingManager accountingManager, Function<AccountingManager, Stream<T>> streamFunction) {
        return streamFunction.apply(accountingManager)
                .sequential()
                .collect(Collectors.toList());
    }

    private boolean isComparableYear(int year) {
        // Imported test dossier only cover those 2 years
        return year == 2016 || year == 2017;
    }

    private boolean isComparablePeriodType(ATPeriodType periodType) {
        // No closing in bob (atm?) //TODO
        return periodType != ATPeriodType.CLOSING;
    }

    private boolean isCOmparableAccountCode(String code) {
        // present in the winbooks import only //FIXME?
        return !code.equals("130017")
                && !code.equals("149999")
                && !code.equals("260000")
                && !code.equals("260009")
                && !code.equals("404000")
                && !code.equals("411100")
                && !code.equals("451800")
                && !code.equals("451300")
                && !code.equals("451350")
                && !code.equals("451500")
                && !code.equals("499999")
                && !code.equals("610001")
                && !code.equals("611650")
                && !code.equals("612401")
                && !code.equals("612402")
                && !code.equals("612601")
                && !code.equals("612602")
                && !code.equals("613110")
                && !code.equals("615700")
                && !code.equals("615800")
                && !code.equals("618500")
                && !code.equals("618510")
                && !code.equals("618520")
                && !code.equals("630210")
                && !code.equals("640120")
                && !code.equals("650040")
                && !code.equals("650060")
                && !code.equals("650100")
                && !code.equals("650200")
                && !code.equals("640500")
                && !code.equals("654100")
                && !code.equals("656100")
                && !code.equals("657001")
                && !code.equals("657010")
                && !code.equals("657100")
                && !code.equals("670250")
                && !code.equals("657200")
                && !code.equals("700110")
                && !code.equals("743500")
                && !code.equals("743501")
                && !code.equals("743510")
                && !code.equals("743520")
                && !code.equals("754100")
                && !code.equals("756100")
                && !code.equals("757010")
                && !code.equals("757200")
                ;
    }
}
