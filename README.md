# iso20022

This library can be used for exporting payment orders (pain001 files) using the iso20022 format for Switzerland.

## Getting Started

These instructions will get you an overview on how to implement and use the iso20022 library. See further down for installing or deployment notes.

### Installing


```xml
<dependency>
    <groupId>ch.dvbern.oss.iso20022</groupId>
    <artifactId>iso20022-impl</artifactId>
    <version>(NEWEST_VERSION)</version>
</dependency>
```

## Usage Example
```java
public class MyCDIEnabledClass {

    @Inject
    private Pain001Service pain001Service;
    
    private static final String STORE_PATH = "target/painDemoFile.xml";
    
    public void createDemoPainFile() {
        Pain001DTO pain001DTO = demoPaymentOrder();
        final byte[] painFileContent = pain001Service.getPainFileContent(pain001DTO);
        writeResultsToFile(painFileContent);
    }
        
    private Pain001DTO demoPaymentOrder() {
        Pain001DTO pain001DTO = new Pain001DTO();
            
        pain001DTO.setAuszahlungsDatum(LocalDate.now());
        pain001DTO.setGenerierungsDatum(LocalDateTime.now());            
        pain001DTO.setSchuldnerName("John Deptor");
        pain001DTO.setSchuldnerIBAN("CH9300762011623852957");
        pain001DTO.setSchuldnerBIC("POFICHBEXXX");
        pain001DTO.setSoftwareName("DVBern Payment Tool");
        pain001DTO.setMsgId("Test-ID");
            
        List<AuszahlungDTO> payments = new ArrayList<>();

        // first paying out
        AuszahlungDTO payment1 = new AuszahlungDTO();
        payment1.setBetragTotalZahlung(BigDecimal.TEN);
        payment1.setZahlungsempfaengerBankClearingNumber("POFICHBEXXX");
        payment1.setZahlungsempfaengerIBAN("CH9300762011623852957");
        payment1.setZahlungsempfaengerLand("CH");
        payment1.setZahlungsempfaengerName("Hans Payee");
        payment1.setZahlungsempfaengerStrasse("Teststreet");
        payment1.setZahlungsempfaengerHausnummer("1");
        payment1.setZahlungsempfaengerPlz("3000");
        payment1.setZahlungsempfaengerOrt("Bern");
        payment1.setZahlungText("This is my first payment");
        payments.add(payment1);

        // second paying out
        AuszahlungDTO payment2 = new AuszahlungDTO();
        payment2.setBetragTotalZahlung(new BigDecimal(1000));
        payment2.setZahlungsempfaengerBankClearingNumber("POFICHBEXXX");
        payment2.setZahlungsempfaengerIBAN("CH9300762011623852957");
        payment2.setZahlungsempfaengerLand("CH");
        payment2.setZahlungsempfaengerName("Kurt Payee");
        payment2.setZahlungsempfaengerStrasse("Teststreet");
        payment2.setZahlungsempfaengerHausnummer("2");
        payment2.setZahlungsempfaengerPlz("4000");
        payment2.setZahlungsempfaengerOrt("Zürich");
        payment1.setZahlungText("This is my second payment");
        payments.add(payment2);
        
        pain001DTO.setAuszahlungen(payments);
        
        return pain001DTO;        
    }
        
    /**
     * Write data to File
    */
    private void writeResultsToFile(byte[] data) throws IOException {
        FileOutputStream fos = new FileOutputStream(STORE_PATH);
        fos.write(data);
        fos.close();
    }
}
    
```
                    
## Built With

* [Maven](https://maven.apache.org/) - Dependency Management


## Contributing Guidelines

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for the process for submitting pull requests to us.

## Code of Conduct

One healthy social atmospehere is very important to us, wherefore we rate our Code of Conduct high.
 For details check the file [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md)

## Authors

* **DV Bern AG** - *Initial work* - [dvbern](https://github.com/dvbern)

See also the list of [contributors](https://github.com/dvbern/iso20022/contributors) who participated in this project.

## License

This project is licensed under the Apache 2.0 License - see the [LICENSE.md](LICENSE.md) file for details.

