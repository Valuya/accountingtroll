package be.valuya.accountingtroll;

import be.valuya.accountingtroll.domain.ATAccount;
import be.valuya.accountingtroll.domain.ATAccountingEntry;
import be.valuya.accountingtroll.domain.ATBookPeriod;
import be.valuya.accountingtroll.domain.ATBookYear;
import be.valuya.accountingtroll.domain.ATPeriodType;
import be.valuya.accountingtroll.domain.ATThirdParty;
import be.valuya.accountingtroll.domain.ATThirdPartyType;
import be.valuya.accountingtroll.domain.AccountingEntryType;
import be.valuya.accountingtroll.event.AccountingEventHandler;
import be.valuya.accountingtroll.event.BalanceChangeEvent;
import be.valuya.bob.core.BobFileConfiguration;
import be.valuya.bob.core.api.troll.MemoryCachingBobAccountingManager;
import be.valuya.winbooks.api.accountingtroll.WinbooksTrollAccountingManager;
import be.valuya.winbooks.api.extra.WinbooksFileConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(JUnit4.class)
public class AccountingTrollImplementationComparisonTest {

    public static final double EPSILON = 0.00000001;
    private Map<String, AccountingManager> accountingManagers = new HashMap<>();

    @Before
    public void init() {
        String bobTestFolderString = System.getProperty("bob.test.folder");
        Path bobTestPath = Paths.get(bobTestFolderString);
        BobFileConfiguration bobFileConfiguration = new BobFileConfiguration(bobTestPath);
        bobFileConfiguration.setReadTablesToMemory(true);
        bobFileConfiguration.setIgnoreOpeningPeriodBalances(true);
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


    @Test
    public void testAccountingEntries() {

        System.out.println("");
        System.out.println("streamAccountingEntries");

        Function<AccountingManager, Stream<ATAccountingEntry>> streamFunction = this::streamManagerAccountingEntries;
        this.testImplementations(streamFunction);


    }


    @Test
    public void testAccountingEntriesBalance() {
        System.out.println("");
        System.out.println("testAccountingEntriesBalance");

        Map<AccountingManager, TestAccountingEventHandler> listenerMap = accountingManagers.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> accountingManagers.get(e.getKey()),
                        f -> new TestAccountingEventHandler(f.getKey())
                ));

        listenerMap.entrySet()
                .forEach(e -> e.getKey().streamAccountingEntries(e.getValue()).count());

        Function<AccountingManager, Stream<BalanceChangeEvent>> balanceChangeEventStreamFunction =
                manager -> listenerMap.get(manager).getLastBalanceByAccount().values().stream()
                        .filter(e -> this.isInAllImplementation(e.getAccount(), listenerMap))
                        .sorted(Comparator
                                .comparing((BalanceChangeEvent e) -> e.getAccount().getCode())
                                .thenComparing((BalanceChangeEvent e) -> e.getAccountingEntryOptional().map(ATAccountingEntry::getDate).orElse(LocalDate.MIN))
                        );

        this.testImplementations(balanceChangeEventStreamFunction);
    }

    private boolean isInAllImplementation(ATAccount account, Map<AccountingManager, TestAccountingEventHandler> listenerMap) {
        return listenerMap.values()
                .stream()
                .allMatch(l -> l.getLastBalanceByAccount().containsKey(account.getCode()));
    }


    private Stream<ATAccountingEntry> streamManagerAccountingEntries(AccountingManager accountingManager) {
        Comparator<ATAccountingEntry> comparator = Comparator
                .comparing((ATAccountingEntry c) -> c.getBookPeriod().getStartDate())
                .thenComparing((ATAccountingEntry c) -> c.getAccount().getCode())
                .thenComparing(ATAccountingEntry::getDate);

        return accountingManager.streamAccountingEntries(new AccountingEventHandler())
                .filter(this::isComparableAccountinEntry)
                .sorted(comparator);
    }


    private <T> void testImplementations(Function<AccountingManager, Stream<T>> testFunction) {
        Map<String, List<T>> implementationsResults = accountingManagers.entrySet()
                .stream()
                .sequential()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
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
        } else if (valA instanceof ATAccountingEntry) {
            return accountingEntriesEqual((ATAccountingEntry) valA, (ATAccountingEntry) valB);
        } else if (valA instanceof BalanceChangeEvent) {
            return balanceChangeEventEquals((BalanceChangeEvent) valA, (BalanceChangeEvent) valB);
        }
        return false;
    }

    private boolean balanceChangeEventEquals(BalanceChangeEvent valA, BalanceChangeEvent valB) {
        ATAccount accountA = valA.getAccount();
        BigDecimal newBalanceA = valA.getNewBalance();
        LocalDate dateA = valA.getDate();

        ATAccount accountB = valB.getAccount();
        BigDecimal newBalanceB = valB.getNewBalance();
        LocalDate dateB = valB.getDate();

        boolean newBalanceEqual = Math.abs(newBalanceA.subtract(newBalanceB).doubleValue()) < EPSILON;

        return accountsEqual(accountA, accountB)
                && newBalanceEqual
                && dateA.equals(dateB);
    }

    private boolean accountingEntriesEqual(ATAccountingEntry valA, ATAccountingEntry valB) {
        LocalDate dateA = valA.getDate();
        Optional<ATThirdParty> thirdPartyOptionalA = valA.getThirdPartyOptional();
        ATAccount accountA = valA.getAccount();
        BigDecimal amountA = valA.getAmount();
        AccountingEntryType typeA = valA.getAccountingEntryType();

        LocalDate dateB = valB.getDate();
        Optional<ATThirdParty> thirdPartyOptionalB = valB.getThirdPartyOptional();
        ATAccount accountB = valB.getAccount();
        BigDecimal amountB = valB.getAmount();
        AccountingEntryType typeB = valB.getAccountingEntryType();

        boolean thirdPartiesEqual;
        if (thirdPartyOptionalA.isPresent() && thirdPartyOptionalB.isPresent()) {
            ATThirdParty thirdPartyA = thirdPartyOptionalA.get();
            ATThirdParty thirdPartyB = thirdPartyOptionalB.get();
            thirdPartiesEqual = thirdPartiesEqual(thirdPartyA, thirdPartyB);
        } else {
            // Ignore third parties on accounts 550000, as winbooks seems to drop them
            ATAccount account = valA.getAccount();
            String accountCode = account.getCode();

            thirdPartiesEqual = accountCode.equals("550000") || thirdPartyOptionalA.isPresent() == thirdPartyOptionalB.isPresent();
        }


        boolean accountsEqual = accountsEqual(accountA, accountB);
        boolean amountEquals = Math.abs(amountA.subtract(amountB).doubleValue()) < EPSILON;

        return dateA.equals(dateB)
                && thirdPartiesEqual
                && accountsEqual
                && amountEquals
                && typeA == typeB
                ;
    }


    private boolean thirdPartiesEqual(ATThirdParty valA, ATThirdParty valB) {
        Optional<ATThirdPartyType> typeOptionalA = valA.getTypeOptional();
        Optional<ATThirdPartyType> typeOptionalB = valB.getTypeOptional();
        boolean typeEquals = typeOptionalA.equals(typeOptionalB);

        Optional<String> languageOptionalA = valA.getLanguageOptional();
        Optional<String> languageOptionalB = valB.getLanguageOptional();
        boolean languagesEqual = optionalStringsEqual(languageOptionalA, languageOptionalB);

        Optional<String> fullNameOptionalA = valA.getFullNameOptional();
        Optional<String> fullNameOptionalB = valB.getFullNameOptional();
        boolean fullNameEqual = optionalStringsEqual(fullNameOptionalA, fullNameOptionalB);

        Optional<String> addressOptionalA = valA.getAddressOptional();
        Optional<String> addressOptionalB = valB.getAddressOptional();
        boolean addressEquals = optionalStringsEqual(addressOptionalA, addressOptionalB);

        return typeEquals
                && languagesEqual
                && fullNameEqual
                && addressEquals;
    }

    private boolean optionalStringsEqual(Optional<String> optionalA, Optional<String> optionalB) {
        Optional<String> filteredA = optionalA.filter(s -> !s.trim().isEmpty());
        Optional<String> filteredB = optionalB.filter(s -> !s.trim().isEmpty());
        return filteredA.equals(filteredB);
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


    private boolean isComparableAccountinEntry(ATAccountingEntry b) {
        return b.getBookPeriod().getPeriodType() == ATPeriodType.GENERAL
                && b.getBookPeriod().getBookYear().getStartDate().getYear() == 2016
                ;
    }


    private class TestAccountingEventHandler extends AccountingEventHandler {

        private String key;
        private Map<String, BalanceChangeEvent> lastBalanceByAccount = new HashMap<>();

        public TestAccountingEventHandler(String key) {
            this.key = key;
        }

        @Override
        public void handleBalanceChangeEvent(BalanceChangeEvent balanceChangeEvent) {
            ATAccount account = balanceChangeEvent.getAccount();
            String code = account.getCode();
            if (code.equals("610300")) {
                System.out.println(key + " " + balanceChangeEvent);
            }
            LocalDate date = balanceChangeEvent.getDate();
            // Only consider balances before book year 2017 for comparison
            if (date.isBefore(LocalDate.of(2017, 10, 1)) && isCOmparableAccountCode(code)) {
                lastBalanceByAccount.put(code, balanceChangeEvent);
            }
        }

        public Map<String, BalanceChangeEvent> getLastBalanceByAccount() {
            return lastBalanceByAccount;
        }
    }
}
