# Backend

### TODO

* Fix wiremock for tests
* Fix async job on @Startup bean
* Fix error on startup
```
2024-09-07 22:24:53,956 ERROR [io.qua.hib.orm.run.sch.SchemaManagementIntegrator] (Hibernate post-boot validation thread for <default>) Failed to run post-boot validation: java.util.NoSuchElementException
	at java.base/java.util.StringTokenizer.nextToken(StringTokenizer.java:347)
	at org.hibernate.tool.schema.extract.internal.AbstractInformationExtractorImpl.addExtractedColumnInformation(AbstractInformationExtractorImpl.java:684)
	at org.hibernate.tool.schema.extract.internal.AbstractInformationExtractorImpl.lambda$populateTablesWithColumns$4(AbstractInformationExtractorImpl.java:663)
	at org.hibernate.tool.schema.extract.internal.InformationExtractorJdbcDatabaseMetaDataImpl.processColumnsResultSet(InformationExtractorJdbcDatabaseMetaDataImpl.java:86)
	at org.hibernate.tool.schema.extract.internal.AbstractInformationExtractorImpl.populateTablesWithColumns(AbstractInformationExtractorImpl.java:649)
	at org.hibernate.tool.schema.extract.internal.AbstractInformationExtractorImpl.lambda$getTables$3(AbstractInformationExtractorImpl.java:577)
	at org.hibernate.tool.schema.extract.internal.InformationExtractorJdbcDatabaseMetaDataImpl.processTableResultSet(InformationExtractorJdbcDatabaseMetaDataImpl.java:70)
	at org.hibernate.tool.schema.extract.internal.AbstractInformationExtractorImpl.getTables(AbstractInformationExtractorImpl.java:570)
	at org.hibernate.tool.schema.extract.internal.DatabaseInformationImpl.getTablesInformation(DatabaseInformationImpl.java:122)
	at org.hibernate.tool.schema.internal.GroupedSchemaValidatorImpl.validateTables(GroupedSchemaValidatorImpl.java:41)
	at org.hibernate.tool.schema.internal.AbstractSchemaValidator.performValidation(AbstractSchemaValidator.java:99)
	at org.hibernate.tool.schema.internal.AbstractSchemaValidator.doValidation(AbstractSchemaValidator.java:77)
	at io.quarkus.hibernate.orm.runtime.schema.SchemaManagementIntegrator.runPostBootValidation(SchemaManagementIntegrator.java:140)
	at io.quarkus.hibernate.orm.runtime.HibernateOrmRecorder$6.run(HibernateOrmRecorder.java:165)
	at java.base/java.lang.Thread.run(Thread.java:833)
```
