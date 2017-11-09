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

	@Inject
    private Pain001Service pain001Service;
	
	private void createPainFile(String debitorName, String debitorBic, String debitorIban, String debitorIbanGebuehren, PaymentOrder paymentOrder) {
		final byte[] painFileContent = pain001Service.getPainFileContent(wrapPaymentOrder(paymentOrder, debitorName, debitorIban, debitorBic, debitorIbanGebuehren));
	}
			
	private Pain001DTO wrapPaymentOrder(PaymentOrder paymentOrder, String debitorName, String debitorIban, String debitorBic, String
        		debitorIbanGebuehren) {
        Pain001DTO pain001DTO = new Pain001DTO();
            
        pain001DTO.setAuszahlungsDatum(paymentOrder.getDatumFaellig());
        pain001DTO.setGenerierungsDatum(LocalDateTime.now());
            
        pain001DTO.setSchuldnerName(debitorName == null ? DEF_DEBTOR_NAME : debitorName);
        pain001DTO.setSchuldnerIBAN(debitorIban == null ? DEF_DEBTOR_IBAN : debitorIban);
        pain001DTO.setSchuldnerBIC(debitorBic == null ? DEF_DEBTOR_BIC : debitorBic);
        pain001DTO.setSchuldnerIBANGebuehren(debtorIbanGebuehren == null ? pain001DTO.getDeptorIBAN() : debitorIbanGebuehren);
        pain001DTO.setSoftwareName("MyAppName");        
        pain001DTO.setMsgId(paymentOrder.getRef());
            
        pain001DTO.setAuszahlungen(new ArrayList<>());
        	paymentOrder.getPayments().stream()
        		.filter(payment -> payment.getTotal().signum() == 1)
        		.forEach(payment -> {
        			AuszahlungDTO auszahlungDTO = new AuszahlungDTO();
        			auszahlungDTO.setBetragTotalZahlung(payments.getTotal());
        			String accountName = payment.readContact().getFullName();
            
        			auszahlungDTO.setZahlungsempfaegerName(accountName);
        			Adresse addressAccountOwner = payment.readContact().getAddress();
        			if (AccountOwner != null) {
        				auszahlungDTO.setZahlungsempfaegerStrasse(addressAccountOwner.getStrasse());
             			auszahlungDTO.setZahlungsempfaegerHausnummer(addressAccountOwner.getHausnummer());
        				auszahlungDTO.setZahlungsempfaegerPlz(addressAccountOwner.getPlz());
            			auszahlungDTO.setZahlungsempfaegerOrt(addressAccountOwner.getOrt());
            		}
            		auszahlungDTO.setZahlungsempfaegerLand("CH");
            		auszahlungDTO.setZahlungText(getPaymentText(payment.getPaymentText()));
            
            		auszahlungDTO.setZahlungsempfaegerIBAN(payment.getIban());
            		auszahlungDTO.setZahlungsempfaegerBankClearingNumber(payment.getIban().extractClearingNumberWithoutLeadingZeros());
            
            		pain001DTO.getAuszahlungen().add(auszahlungDTO);
            	});
            
            return pain001DTO;
        }
            
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

