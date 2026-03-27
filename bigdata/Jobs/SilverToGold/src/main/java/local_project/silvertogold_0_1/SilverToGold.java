// ============================================================================
//
// Copyright (c) 2006-2015, Talend SA
//
// Ce code source a été automatiquement généré par_Talend Open Studio for Big Data
// / Soumis à la Licence Apache, Version 2.0 (la "Licence") ;
// votre utilisation de ce fichier doit respecter les termes de la Licence.
// Vous pouvez obtenir une copie de la Licence sur
// http://www.apache.org/licenses/LICENSE-2.0
// 
// Sauf lorsqu'explicitement prévu par la loi en vigueur ou accepté par écrit, le logiciel
// distribué sous la Licence est distribué "TEL QUEL",
// SANS GARANTIE OU CONDITION D'AUCUNE SORTE, expresse ou implicite.
// Consultez la Licence pour connaître la terminologie spécifique régissant les autorisations et
// les limites prévues par la Licence.


package local_project.silvertogold_0_1;

import routines.Numeric;
import routines.DataOperation;
import routines.TalendDataGenerator;
import routines.TalendStringUtil;
import routines.TalendString;
import routines.StringHandling;
import routines.Relational;
import routines.TalendDate;
import routines.Mathematical;
import routines.system.*;
import routines.system.api.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Comparator;
 





@SuppressWarnings("unused")

/**
 * Job: SilverToGold Purpose: <br>
 * Description:  <br>
 * @author user@talend.com
 * @version 8.0.1.20211109_1610
 * @status 
 */
public class SilverToGold implements TalendJob {

protected static void logIgnoredError(String message, Throwable cause) {
       System.err.println(message);
       if (cause != null) {
               cause.printStackTrace();
       }

}


	public final Object obj = new Object();

	// for transmiting parameters purpose
	private Object valueObject = null;

	public Object getValueObject() {
		return this.valueObject;
	}

	public void setValueObject(Object valueObject) {
		this.valueObject = valueObject;
	}
	
	private final static String defaultCharset = java.nio.charset.Charset.defaultCharset().name();

	
	private final static String utf8Charset = "UTF-8";
	//contains type for every context property
	public class PropertiesWithType extends java.util.Properties {
		private static final long serialVersionUID = 1L;
		private java.util.Map<String,String> propertyTypes = new java.util.HashMap<>();
		
		public PropertiesWithType(java.util.Properties properties){
			super(properties);
		}
		public PropertiesWithType(){
			super();
		}
		
		public void setContextType(String key, String type) {
			propertyTypes.put(key,type);
		}
	
		public String getContextType(String key) {
			return propertyTypes.get(key);
		}
	}
	
	// create and load default properties
	private java.util.Properties defaultProps = new java.util.Properties();
	// create application properties with default
	public class ContextProperties extends PropertiesWithType {

		private static final long serialVersionUID = 1L;

		public ContextProperties(java.util.Properties properties){
			super(properties);
		}
		public ContextProperties(){
			super();
		}

		public void synchronizeContext(){
			
		}
		
		//if the stored or passed value is "<TALEND_NULL>" string, it mean null
		public String getStringValue(String key) {
			String origin_value = this.getProperty(key);
			if(NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY.equals(origin_value)) {
				return null;
			}
			return origin_value;
		}

	}
	protected ContextProperties context = new ContextProperties(); // will be instanciated by MS.
	public ContextProperties getContext() {
		return this.context;
	}
	private final String jobVersion = "0.1";
	private final String jobName = "SilverToGold";
	private final String projectName = "LOCAL_PROJECT";
	public Integer errorCode = null;
	private String currentComponent = "";
	
		private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
        private final static java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();
	
		private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
		private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
		private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
		public  final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();
	

private RunStat runStat = new RunStat();

	// OSGi DataSource
	private final static String KEY_DB_DATASOURCES = "KEY_DB_DATASOURCES";
	
	private final static String KEY_DB_DATASOURCES_RAW = "KEY_DB_DATASOURCES_RAW";

	public void setDataSources(java.util.Map<String, javax.sql.DataSource> dataSources) {
		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		for (java.util.Map.Entry<String, javax.sql.DataSource> dataSourceEntry : dataSources.entrySet()) {
			talendDataSources.put(dataSourceEntry.getKey(), new routines.system.TalendDataSource(dataSourceEntry.getValue()));
		}
		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}
	
	public void setDataSourceReferences(List serviceReferences) throws Exception{
		
		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		java.util.Map<String, javax.sql.DataSource> dataSources = new java.util.HashMap<String, javax.sql.DataSource>();
		
		for (java.util.Map.Entry<String, javax.sql.DataSource> entry : BundleUtils.getServices(serviceReferences,  javax.sql.DataSource.class).entrySet()) {
                    dataSources.put(entry.getKey(), entry.getValue());
                    talendDataSources.put(entry.getKey(), new routines.system.TalendDataSource(entry.getValue()));
		}

		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}


private final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
private final java.io.PrintStream errorMessagePS = new java.io.PrintStream(new java.io.BufferedOutputStream(baos));

public String getExceptionStackTrace() {
	if ("failure".equals(this.getStatus())) {
		errorMessagePS.flush();
		return baos.toString();
	}
	return null;
}

private Exception exception;

public Exception getException() {
	if ("failure".equals(this.getStatus())) {
		return this.exception;
	}
	return null;
}

private class TalendException extends Exception {

	private static final long serialVersionUID = 1L;

	private java.util.Map<String, Object> globalMap = null;
	private Exception e = null;
	private String currentComponent = null;
	private String virtualComponentName = null;
	
	public void setVirtualComponentName (String virtualComponentName){
		this.virtualComponentName = virtualComponentName;
	}

	private TalendException(Exception e, String errorComponent, final java.util.Map<String, Object> globalMap) {
		this.currentComponent= errorComponent;
		this.globalMap = globalMap;
		this.e = e;
	}

	public Exception getException() {
		return this.e;
	}

	public String getCurrentComponent() {
		return this.currentComponent;
	}

	
    public String getExceptionCauseMessage(Exception e){
        Throwable cause = e;
        String message = null;
        int i = 10;
        while (null != cause && 0 < i--) {
            message = cause.getMessage();
            if (null == message) {
                cause = cause.getCause();
            } else {
                break;          
            }
        }
        if (null == message) {
            message = e.getClass().getName();
        }   
        return message;
    }

	@Override
	public void printStackTrace() {
		if (!(e instanceof TalendException || e instanceof TDieException)) {
			if(virtualComponentName!=null && currentComponent.indexOf(virtualComponentName+"_")==0){
				globalMap.put(virtualComponentName+"_ERROR_MESSAGE",getExceptionCauseMessage(e));
			}
			globalMap.put(currentComponent+"_ERROR_MESSAGE",getExceptionCauseMessage(e));
			System.err.println("Exception in component " + currentComponent + " (" + jobName + ")");
		}
		if (!(e instanceof TDieException)) {
			if(e instanceof TalendException){
				e.printStackTrace();
			} else {
				e.printStackTrace();
				e.printStackTrace(errorMessagePS);
				SilverToGold.this.exception = e;
			}
		}
		if (!(e instanceof TalendException)) {
		try {
			for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
				if (m.getName().compareTo(currentComponent + "_error") == 0) {
					m.invoke(SilverToGold.this, new Object[] { e , currentComponent, globalMap});
					break;
				}
			}

			if(!(e instanceof TDieException)){
			}
		} catch (Exception e) {
			this.e.printStackTrace();
		}
		}
	}
}

			public void tMongoDBConnection_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBConnection_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tMongoDBInput_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tMap_3_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tMap_8_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tLogRow_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBOutput_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBCommit_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBCommit_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tMongoDBInput_2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_2_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tNormalize_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_2_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tMap_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_2_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tMap_5_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_2_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tMap_7_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_2_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBOutput_3_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_2_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBCommit_2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBCommit_2_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tFixedFlowInput_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_3_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tMap_2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_3_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tLogRow_3_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_3_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tMongoDBInput_3_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_3_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tNormalize_2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_3_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tMap_6_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_3_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tMap_4_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_3_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tLogRow_2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_3_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tSampleRow_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_3_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tDBConnection_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tDBConnection_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tAdvancedHash_row9_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_3_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tAdvancedHash_row12_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_3_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tAggregateRow_1_AGGOUT_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
							tAggregateRow_1_AGGIN_error(exception, errorComponent, globalMap);
						
						}
					
			public void tAggregateRow_1_AGGIN_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_3_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tSortRow_2_SortOut_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
							tSortRow_2_SortIn_error(exception, errorComponent, globalMap);
						
						}
					
			public void tSortRow_2_SortIn_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_3_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tMongoDBConnection_1_onSubJobError(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {

resumeUtil.addLog("SYSTEM_LOG", "NODE:"+ errorComponent, "", Thread.currentThread().getId()+ "", "FATAL", "", exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception),"");

			}
			public void tMongoDBInput_1_onSubJobError(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {

resumeUtil.addLog("SYSTEM_LOG", "NODE:"+ errorComponent, "", Thread.currentThread().getId()+ "", "FATAL", "", exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception),"");

			}
			public void tDBCommit_1_onSubJobError(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {

resumeUtil.addLog("SYSTEM_LOG", "NODE:"+ errorComponent, "", Thread.currentThread().getId()+ "", "FATAL", "", exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception),"");

			}
			public void tMongoDBInput_2_onSubJobError(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {

resumeUtil.addLog("SYSTEM_LOG", "NODE:"+ errorComponent, "", Thread.currentThread().getId()+ "", "FATAL", "", exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception),"");

			}
			public void tDBCommit_2_onSubJobError(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {

resumeUtil.addLog("SYSTEM_LOG", "NODE:"+ errorComponent, "", Thread.currentThread().getId()+ "", "FATAL", "", exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception),"");

			}
			public void tMongoDBInput_3_onSubJobError(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {

resumeUtil.addLog("SYSTEM_LOG", "NODE:"+ errorComponent, "", Thread.currentThread().getId()+ "", "FATAL", "", exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception),"");

			}
			public void tDBConnection_1_onSubJobError(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {

resumeUtil.addLog("SYSTEM_LOG", "NODE:"+ errorComponent, "", Thread.currentThread().getId()+ "", "FATAL", "", exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception),"");

			}
	





public void tMongoDBConnection_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tMongoDBConnection_1_SUBPROCESS_STATE", 0);

 final boolean execStat = this.execStat;
	
		String iterateId = "";
	
	
	String currentComponent = "";
	java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

	try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { //start the resume
				globalResumeTicket = true;



		


	
	/**
	 * [tMongoDBConnection_1 begin ] start
	 */

	

	
		
		ok_Hash.put("tMongoDBConnection_1", false);
		start_Hash.put("tMongoDBConnection_1", System.currentTimeMillis());
		
	
	currentComponent="tMongoDBConnection_1";

	
		int tos_count_tMongoDBConnection_1 = 0;
		

        java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(java.util.logging.Level.SEVERE);
        
    

final String applicationName_tMongoDBConnection_1 = "Talend";

    // Declarations
    com.mongodb.client.MongoClient mongo_tMongoDBConnection_1=null;
    com.mongodb.client.MongoDatabase db_tMongoDBConnection_1=null;
    List<com.mongodb.ServerAddress> addrs_tMongoDBConnection_1 = new java.util.ArrayList<>();
    com.mongodb.MongoClientSettings.Builder clientSettingsBuilder_tMongoDBConnection_1 = com.mongodb.MongoClientSettings.builder().applicationName(applicationName_tMongoDBConnection_1);
    com.mongodb.connection.ClusterSettings.Builder clusterSettingsBuilder_tMongoDBConnection_1 = com.mongodb.connection.ClusterSettings.builder();
    com.mongodb.connection.ConnectionPoolSettings.Builder connPoolSettingsBuilder_tMongoDBConnection_1 = com.mongodb.connection.ConnectionPoolSettings.builder();
    com.mongodb.connection.ServerSettings.Builder serverSettingsBuilder_tMongoDBConnection_1 = com.mongodb.connection.ServerSettings.builder();
    com.mongodb.connection.SocketSettings.Builder socketSettingsBuilder_tMongoDBConnection_1 = com.mongodb.connection.SocketSettings.builder();
    com.mongodb.connection.SslSettings.Builder sslSettingsBuilder_tMongoDBConnection_1 = com.mongodb.connection.SslSettings.builder();



                // SSL

                // Client Credentials
                    // Authentication
                    com.mongodb.MongoCredential mongoCredential_tMongoDBConnection_1; 
	final String decryptedPassword_tMongoDBConnection_1 = routines.system.PasswordEncryptUtil.decryptPassword("enc:routine.encryption.key.v1:1mtTDetUiA1IFQcCPJ0HJheRiu2Pr3fDz7ZHWv0LP8E8EpXCi1QRkQ==");
                        
	                        mongoCredential_tMongoDBConnection_1 = com.mongodb.MongoCredential.createScramSha256Credential("fimafeng", "ville-t", new String(decryptedPassword_tMongoDBConnection_1).toCharArray());
	                        
                    clientSettingsBuilder_tMongoDBConnection_1.credential(mongoCredential_tMongoDBConnection_1);
                    addrs_tMongoDBConnection_1.add(new com.mongodb.ServerAddress("localhost", 27017));
                clusterSettingsBuilder_tMongoDBConnection_1.hosts(addrs_tMongoDBConnection_1);

                clientSettingsBuilder_tMongoDBConnection_1.applyToClusterSettings(builder -> builder.applySettings(clusterSettingsBuilder_tMongoDBConnection_1.build()));




    mongo_tMongoDBConnection_1 = com.mongodb.client.MongoClients.create(clientSettingsBuilder_tMongoDBConnection_1.build());
    resourceMap.put("mongo_tMongoDBConnection_1", mongo_tMongoDBConnection_1);
    db_tMongoDBConnection_1 = mongo_tMongoDBConnection_1.getDatabase("ville-t");


    globalMap.put("mongo_tMongoDBConnection_1", mongo_tMongoDBConnection_1);
    globalMap.put("db_tMongoDBConnection_1", db_tMongoDBConnection_1);


 



/**
 * [tMongoDBConnection_1 begin ] stop
 */
	
	/**
	 * [tMongoDBConnection_1 main ] start
	 */

	

	
	
	currentComponent="tMongoDBConnection_1";

	

 


	tos_count_tMongoDBConnection_1++;

/**
 * [tMongoDBConnection_1 main ] stop
 */
	
	/**
	 * [tMongoDBConnection_1 process_data_begin ] start
	 */

	

	
	
	currentComponent="tMongoDBConnection_1";

	

 



/**
 * [tMongoDBConnection_1 process_data_begin ] stop
 */
	
	/**
	 * [tMongoDBConnection_1 process_data_end ] start
	 */

	

	
	
	currentComponent="tMongoDBConnection_1";

	

 



/**
 * [tMongoDBConnection_1 process_data_end ] stop
 */
	
	/**
	 * [tMongoDBConnection_1 end ] start
	 */

	

	
	
	currentComponent="tMongoDBConnection_1";

	

 

ok_Hash.put("tMongoDBConnection_1", true);
end_Hash.put("tMongoDBConnection_1", System.currentTimeMillis());

				if(execStat){   
   	 				runStat.updateStatOnConnection("OnComponentOk1", 0, "ok");
				}
				tMongoDBInput_1Process(globalMap);
				if(execStat){   
   	 				runStat.updateStatOnConnection("OnComponentOk2", 0, "ok");
				}
				tMongoDBInput_2Process(globalMap);



/**
 * [tMongoDBConnection_1 end ] stop
 */
				}//end the resume

				



	
			}catch(java.lang.Exception e){	
				
				TalendException te = new TalendException(e, currentComponent, globalMap);
				
				throw te;
			}catch(java.lang.Error error){	
				
					runStat.stopThreadStat();
				
				throw error;
			}finally{
				
				try{
					
	
	/**
	 * [tMongoDBConnection_1 finally ] start
	 */

	

	
	
	currentComponent="tMongoDBConnection_1";

	

 



/**
 * [tMongoDBConnection_1 finally ] stop
 */
				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tMongoDBConnection_1_SUBPROCESS_STATE", 1);
	}
	


public static class row2Struct implements routines.system.IPersistableRow<row2Struct> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_SilverToGold = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[0];

	
			    public String announce_id;

				public String getAnnounce_id () {
					return this.announce_id;
				}
				
			    public Integer views;

				public Integer getViews () {
					return this.views;
				}
				
			    public Integer participations;

				public Integer getParticipations () {
					return this.participations;
				}
				



	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }
	private Integer readInteger(ObjectInputStream dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}
	
	private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
    	}
	}

    public void readData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.announce_id = readString(dis);
					
						this.views = readInteger(dis);
					
						this.participations = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.announce_id = readString(dis);
					
						this.views = readInteger(dis);
					
						this.participations = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.announce_id,dos);
					
					// Integer
				
						writeInteger(this.views,dos);
					
					// Integer
				
						writeInteger(this.participations,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.announce_id,dos);
					
					// Integer
				
						writeInteger(this.views,dos);
					
					// Integer
				
						writeInteger(this.participations,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("announce_id="+announce_id);
		sb.append(",views="+String.valueOf(views));
		sb.append(",participations="+String.valueOf(participations));
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row2Struct other) {

		int returnValue = -1;
		
	    return returnValue;
    }


    private int checkNullsAndCompare(Object object1, Object object2) {
        int returnValue = 0;
		if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }


}

public static class copyOfout3Struct implements routines.system.IPersistableRow<copyOfout3Struct> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_SilverToGold = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[0];

	
			    public String announce_id;

				public String getAnnounce_id () {
					return this.announce_id;
				}
				
			    public Integer views;

				public Integer getViews () {
					return this.views;
				}
				
			    public Integer participations;

				public Integer getParticipations () {
					return this.participations;
				}
				



	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }
	private Integer readInteger(ObjectInputStream dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}
	
	private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
    	}
	}

    public void readData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.announce_id = readString(dis);
					
						this.views = readInteger(dis);
					
						this.participations = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.announce_id = readString(dis);
					
						this.views = readInteger(dis);
					
						this.participations = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.announce_id,dos);
					
					// Integer
				
						writeInteger(this.views,dos);
					
					// Integer
				
						writeInteger(this.participations,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.announce_id,dos);
					
					// Integer
				
						writeInteger(this.views,dos);
					
					// Integer
				
						writeInteger(this.participations,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("announce_id="+announce_id);
		sb.append(",views="+String.valueOf(views));
		sb.append(",participations="+String.valueOf(participations));
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(copyOfout3Struct other) {

		int returnValue = -1;
		
	    return returnValue;
    }


    private int checkNullsAndCompare(Object object1, Object object2) {
        int returnValue = 0;
		if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }


}

public static class out3Struct implements routines.system.IPersistableRow<out3Struct> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_SilverToGold = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[0];

	
			    public String announce_id;

				public String getAnnounce_id () {
					return this.announce_id;
				}
				
			    public Integer views;

				public Integer getViews () {
					return this.views;
				}
				
			    public Integer participations;

				public Integer getParticipations () {
					return this.participations;
				}
				



	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }
	private Integer readInteger(ObjectInputStream dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}
	
	private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
    	}
	}

    public void readData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.announce_id = readString(dis);
					
						this.views = readInteger(dis);
					
						this.participations = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.announce_id = readString(dis);
					
						this.views = readInteger(dis);
					
						this.participations = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.announce_id,dos);
					
					// Integer
				
						writeInteger(this.views,dos);
					
					// Integer
				
						writeInteger(this.participations,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.announce_id,dos);
					
					// Integer
				
						writeInteger(this.views,dos);
					
					// Integer
				
						writeInteger(this.participations,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("announce_id="+announce_id);
		sb.append(",views="+String.valueOf(views));
		sb.append(",participations="+String.valueOf(participations));
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(out3Struct other) {

		int returnValue = -1;
		
	    return returnValue;
    }


    private int checkNullsAndCompare(Object object1, Object object2) {
        int returnValue = 0;
		if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }


}

public static class row1Struct implements routines.system.IPersistableRow<row1Struct> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_SilverToGold = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[0];

	
			    public String video_id;

				public String getVideo_id () {
					return this.video_id;
				}
				
			    public String view_count;

				public String getView_count () {
					return this.view_count;
				}
				
			    public String like_count;

				public String getLike_count () {
					return this.like_count;
				}
				



	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }

    public void readData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.video_id = readString(dis);
					
					this.view_count = readString(dis);
					
					this.like_count = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.video_id = readString(dis);
					
					this.view_count = readString(dis);
					
					this.like_count = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.video_id,dos);
					
					// String
				
						writeString(this.view_count,dos);
					
					// String
				
						writeString(this.like_count,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.video_id,dos);
					
					// String
				
						writeString(this.view_count,dos);
					
					// String
				
						writeString(this.like_count,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("video_id="+video_id);
		sb.append(",view_count="+view_count);
		sb.append(",like_count="+like_count);
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row1Struct other) {

		int returnValue = -1;
		
	    return returnValue;
    }


    private int checkNullsAndCompare(Object object1, Object object2) {
        int returnValue = 0;
		if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }


}
public void tMongoDBInput_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tMongoDBInput_1_SUBPROCESS_STATE", 0);

 final boolean execStat = this.execStat;
	
		String iterateId = "";
	
	
	String currentComponent = "";
	java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

	try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { //start the resume
				globalResumeTicket = true;



		row1Struct row1 = new row1Struct();
out3Struct out3 = new out3Struct();
copyOfout3Struct copyOfout3 = new copyOfout3Struct();
copyOfout3Struct row2 = copyOfout3;







	
	/**
	 * [tDBOutput_1 begin ] start
	 */

	

	
		
		ok_Hash.put("tDBOutput_1", false);
		start_Hash.put("tDBOutput_1", System.currentTimeMillis());
		
	
	currentComponent="tDBOutput_1";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row2");
					}
				
		int tos_count_tDBOutput_1 = 0;
		





String dbschema_tDBOutput_1 = null;
	dbschema_tDBOutput_1 = (String)globalMap.get("schema_" + "tDBConnection_1");
	

String tableName_tDBOutput_1 = null;
if(dbschema_tDBOutput_1 == null || dbschema_tDBOutput_1.trim().length() == 0) {
	tableName_tDBOutput_1 = ("data_gold_consultation");
} else {
	tableName_tDBOutput_1 = dbschema_tDBOutput_1 + "\".\"" + ("data_gold_consultation");
}


int nb_line_tDBOutput_1 = 0;
int nb_line_update_tDBOutput_1 = 0;
int nb_line_inserted_tDBOutput_1 = 0;
int nb_line_deleted_tDBOutput_1 = 0;
int nb_line_rejected_tDBOutput_1 = 0;

int deletedCount_tDBOutput_1=0;
int updatedCount_tDBOutput_1=0;
int insertedCount_tDBOutput_1=0;
int rowsToCommitCount_tDBOutput_1=0;
int rejectedCount_tDBOutput_1=0;

boolean whetherReject_tDBOutput_1 = false;

java.sql.Connection conn_tDBOutput_1 = null;
String dbUser_tDBOutput_1 = null;

	conn_tDBOutput_1 = (java.sql.Connection)globalMap.get("conn_tDBConnection_1");
	
	


   int batchSize_tDBOutput_1 = 10000;
   int batchSizeCounter_tDBOutput_1=0;

int count_tDBOutput_1=0;
            try (java.sql.Statement stmtClear_tDBOutput_1 = conn_tDBOutput_1.createStatement()) {
                stmtClear_tDBOutput_1.executeUpdate("DELETE FROM \"" + tableName_tDBOutput_1 + "\"");
            }
	    String insert_tDBOutput_1 = "INSERT INTO \"" + tableName_tDBOutput_1 + "\" (\"announce_id\",\"views\",\"participations\") VALUES (?,?,?)";
	    
	    java.sql.PreparedStatement pstmt_tDBOutput_1 = conn_tDBOutput_1.prepareStatement(insert_tDBOutput_1);
	    resourceMap.put("pstmt_tDBOutput_1", pstmt_tDBOutput_1);
	    

 



/**
 * [tDBOutput_1 begin ] stop
 */



	
	/**
	 * [tLogRow_1 begin ] start
	 */

	

	
		
		ok_Hash.put("tLogRow_1", false);
		start_Hash.put("tLogRow_1", System.currentTimeMillis());
		
	
	currentComponent="tLogRow_1";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"copyOfout3");
					}
				
		int tos_count_tLogRow_1 = 0;
		

	///////////////////////
	
		final String OUTPUT_FIELD_SEPARATOR_tLogRow_1 = "|";
		java.io.PrintStream consoleOut_tLogRow_1 = null;	

 		StringBuilder strBuffer_tLogRow_1 = null;
		int nb_line_tLogRow_1 = 0;
///////////////////////    			



 



/**
 * [tLogRow_1 begin ] stop
 */



	
	/**
	 * [tMap_8 begin ] start
	 */

	

	
		
		ok_Hash.put("tMap_8", false);
		start_Hash.put("tMap_8", System.currentTimeMillis());
		
	
	currentComponent="tMap_8";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"out3");
					}
				
		int tos_count_tMap_8 = 0;
		




// ###############################
// # Lookup's keys initialization
// ###############################        

// ###############################
// # Vars initialization
class  Var__tMap_8__Struct  {
}
Var__tMap_8__Struct Var__tMap_8 = new Var__tMap_8__Struct();
// ###############################

// ###############################
// # Outputs initialization
copyOfout3Struct copyOfout3_tmp = new copyOfout3Struct();
// ###############################

        
        



        









 



/**
 * [tMap_8 begin ] stop
 */



	
	/**
	 * [tMap_3 begin ] start
	 */

	

	
		
		ok_Hash.put("tMap_3", false);
		start_Hash.put("tMap_3", System.currentTimeMillis());
		
	
	currentComponent="tMap_3";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row1");
					}
				
		int tos_count_tMap_3 = 0;
		




// ###############################
// # Lookup's keys initialization
// ###############################        

// ###############################
// # Vars initialization
class  Var__tMap_3__Struct  {
}
Var__tMap_3__Struct Var__tMap_3 = new Var__tMap_3__Struct();
// ###############################

// ###############################
// # Outputs initialization
out3Struct out3_tmp = new out3Struct();
// ###############################

        
        



        









 



/**
 * [tMap_3 begin ] stop
 */



	
	/**
	 * [tMongoDBInput_1 begin ] start
	 */

	

	
		
		ok_Hash.put("tMongoDBInput_1", false);
		start_Hash.put("tMongoDBInput_1", System.currentTimeMillis());
		
	
	currentComponent="tMongoDBInput_1";

	
		int tos_count_tMongoDBInput_1 = 0;
		


	

final String applicationName_tMongoDBInput_1 = "Talend";

	int nb_line_tMongoDBInput_1 = 0;
	


    // Declarations
    com.mongodb.client.MongoClient mongo_tMongoDBInput_1=null;
    com.mongodb.client.MongoDatabase db_tMongoDBInput_1=null;

        mongo_tMongoDBInput_1=(com.mongodb.client.MongoClient)globalMap.get("mongo_tMongoDBConnection_1");
        db_tMongoDBInput_1 = (com.mongodb.client.MongoDatabase)globalMap.get("db_tMongoDBConnection_1");


				
					com.mongodb.client.MongoCollection<org.bson.Document> coll_tMongoDBInput_1 = db_tMongoDBInput_1.getCollection("data_silver_consultation");
				
				
				
				try{
					// Add warning if an index is not in the query.
					boolean needIndexWarning = true;
					String indexList = "";
					java.lang.StringBuilder sb_tMongoDBInput_1 = new java.lang.StringBuilder();
	                
	                    for (com.mongodb.DBObject index: coll_tMongoDBInput_1.listIndexes(com.mongodb.DBObject.class)) {
	                 
	                        for (String key: ((com.mongodb.DBObject)index.get("key")).keySet()) {
	                            // The regexp is:
	                            // - contain the db DBcolumnName between two backslashed quotes
	                            // - is followed at some point by a colon
	                            // - there is no comma between the the DBcolumnName and the colon
	                            if  (("{}").matches(".*" + key.replace("*","\\*") + "[^,]*:.*")) {
	                                // We have an index, do not print error message
	                                needIndexWarning = false;
	                            } else {
	                                // This index is not in the query, add it into the indexList
	                                sb_tMongoDBInput_1.append(", ").append(key);
	                            }
	                        }
	                        indexList = sb_tMongoDBInput_1.toString();
	                    }
	                if ((!"".equals(indexList)) && (needIndexWarning)) {
	                    
	                        System.err.println("tMongoDBInput_1 - The query does not contain any reference an index.  [" + indexList.substring(1) + " ]");
	                        
	                }
	            }catch(com.mongodb.MongoException e){
	            	// caught an exception after issuing the getIndexInfo()
	            	// don't fail the whole job
	            	// maybe due to authorization
	            }

	                	
							java.util.List<org.bson.conversions.Bson> aggregationStages_tMongoDBInput_1 = new java.util.ArrayList<>();
						
	                	
	                
						com.mongodb.client.MongoCursor<org.bson.Document> cursor_tMongoDBInput_1 = coll_tMongoDBInput_1.aggregate(aggregationStages_tMongoDBInput_1).allowDiskUse(false).iterator();
						


				
				class DBObjectInputUtil_tMongoDBInput_1{
					// Get the node value in embedded document, 
					//If have no embedded document get root document node.
					
					public Object getValue(String parentNode,String currentName,org.bson.Document dbObject){
						Object value=null;
						if(dbObject==null){
							return null;
						}
						if (parentNode == null || "".equals(parentNode)) {
						    if ("*".equals(currentName)) {
						        value = dbObject;
						    } else if (dbObject.get(currentName)!=null){
								value=dbObject.get(currentName);
							}
						}else{
							String objNames[] = parentNode.split("\\.");
							org.bson.Document currentObj=dbObject;
							for(int i=0;i<objNames.length;i++){
								currentObj=(org.bson.Document)currentObj.get(objNames[i]);
								if(currentObj==null){
									break;
								}
							}
							if ("*".equals(currentName)) {
                                value = currentObj;
                            } else if(currentObj!=null){
								value=currentObj.get(currentName);
							}
						}
						
						    if(value instanceof org.bson.Document){
						        value = ((org.bson.Document)value).toJson();
						    }else if (value instanceof java.util.List){

						    java.util.List list = new java.util.ArrayList();
						    ((java.util.List)value).stream().forEach(e -> {
						    if(e instanceof org.bson.Document){
						        list.add(((org.bson.Document)e).toJson());
						    }else{
						        list.add(e);
						    }
						    });
						    value = list;
						    }

						
						return value;
					}
				}
				DBObjectInputUtil_tMongoDBInput_1 dbObjectInputUtil_tMongoDBInput_1=new DBObjectInputUtil_tMongoDBInput_1();
				java.util.Map<String, String> pathMap_tMongoDBInput_1=new java.util.HashMap<>();
				pathMap_tMongoDBInput_1.put("video_id","");
				pathMap_tMongoDBInput_1.put("view_count","");
				pathMap_tMongoDBInput_1.put("like_count","");

						
				while (cursor_tMongoDBInput_1.hasNext()){
				org.bson.Document o_tMongoDBInput_1 = cursor_tMongoDBInput_1.next();
				nb_line_tMongoDBInput_1++;
				Object valueObj_tMongoDBInput_1=null;
                    valueObj_tMongoDBInput_1=dbObjectInputUtil_tMongoDBInput_1.getValue(pathMap_tMongoDBInput_1.get("video_id"),"video_id",o_tMongoDBInput_1);
					
				row1.video_id = valueObj_tMongoDBInput_1==null ? null : valueObj_tMongoDBInput_1.toString();
				
                    valueObj_tMongoDBInput_1=dbObjectInputUtil_tMongoDBInput_1.getValue(pathMap_tMongoDBInput_1.get("view_count"),"view_count",o_tMongoDBInput_1);
					
				row1.view_count = valueObj_tMongoDBInput_1==null ? null : valueObj_tMongoDBInput_1.toString();
				
                    valueObj_tMongoDBInput_1=dbObjectInputUtil_tMongoDBInput_1.getValue(pathMap_tMongoDBInput_1.get("like_count"),"like_count",o_tMongoDBInput_1);
					
				row1.like_count = valueObj_tMongoDBInput_1==null ? null : valueObj_tMongoDBInput_1.toString();
				


 



/**
 * [tMongoDBInput_1 begin ] stop
 */
	
	/**
	 * [tMongoDBInput_1 main ] start
	 */

	

	
	
	currentComponent="tMongoDBInput_1";

	

 


	tos_count_tMongoDBInput_1++;

/**
 * [tMongoDBInput_1 main ] stop
 */
	
	/**
	 * [tMongoDBInput_1 process_data_begin ] start
	 */

	

	
	
	currentComponent="tMongoDBInput_1";

	

 



/**
 * [tMongoDBInput_1 process_data_begin ] stop
 */

	
	/**
	 * [tMap_3 main ] start
	 */

	

	
	
	currentComponent="tMap_3";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row1"
						
						);
					}
					

		
		
		boolean hasCasePrimitiveKeyWithNull_tMap_3 = false;
		

        // ###############################
        // # Input tables (lookups)
		  boolean rejectedInnerJoin_tMap_3 = false;
		  boolean mainRowRejected_tMap_3 = false;
            				    								  
		// ###############################
        { // start of Var scope
        
	        // ###############################
        	// # Vars tables
        
Var__tMap_3__Struct Var = Var__tMap_3;// ###############################
        // ###############################
        // # Output tables

out3 = null;


// # Output table : 'out3'
out3_tmp.announce_id = row1.video_id ;
out3_tmp.views = Integer.parseInt(row1.view_count);
out3_tmp.participations = Integer.parseInt(row1.like_count);
out3 = out3_tmp;
// ###############################

} // end of Var scope

rejectedInnerJoin_tMap_3 = false;










 


	tos_count_tMap_3++;

/**
 * [tMap_3 main ] stop
 */
	
	/**
	 * [tMap_3 process_data_begin ] start
	 */

	

	
	
	currentComponent="tMap_3";

	

 



/**
 * [tMap_3 process_data_begin ] stop
 */
// Start of branch "out3"
if(out3 != null) { 



	
	/**
	 * [tMap_8 main ] start
	 */

	

	
	
	currentComponent="tMap_8";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"out3"
						
						);
					}
					

		
		
		boolean hasCasePrimitiveKeyWithNull_tMap_8 = false;
		

        // ###############################
        // # Input tables (lookups)
		  boolean rejectedInnerJoin_tMap_8 = false;
		  boolean mainRowRejected_tMap_8 = false;
            				    								  
		// ###############################
        { // start of Var scope
        
	        // ###############################
        	// # Vars tables
        
Var__tMap_8__Struct Var = Var__tMap_8;// ###############################
        // ###############################
        // # Output tables

copyOfout3 = null;


// # Output table : 'copyOfout3'
copyOfout3_tmp.announce_id = row1.video_id ;
copyOfout3_tmp.views = (out3.views > 1000000000) ? (out3.views / 100000) : (out3.views / 1000);
copyOfout3_tmp.participations = out3.participations  / 100;
copyOfout3 = copyOfout3_tmp;
// ###############################

} // end of Var scope

rejectedInnerJoin_tMap_8 = false;










 


	tos_count_tMap_8++;

/**
 * [tMap_8 main ] stop
 */
	
	/**
	 * [tMap_8 process_data_begin ] start
	 */

	

	
	
	currentComponent="tMap_8";

	

 



/**
 * [tMap_8 process_data_begin ] stop
 */
// Start of branch "copyOfout3"
if(copyOfout3 != null) { 



	
	/**
	 * [tLogRow_1 main ] start
	 */

	

	
	
	currentComponent="tLogRow_1";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"copyOfout3"
						
						);
					}
					
///////////////////////		
						



				strBuffer_tLogRow_1 = new StringBuilder();




   				
	    		if(copyOfout3.announce_id != null) { //              
                    							
       
				strBuffer_tLogRow_1.append(
				                String.valueOf(copyOfout3.announce_id)							
				);


							
	    		} //  			

    			strBuffer_tLogRow_1.append("|");
    			


   				
	    		if(copyOfout3.views != null) { //              
                    							
       
				strBuffer_tLogRow_1.append(
				                String.valueOf(copyOfout3.views)							
				);


							
	    		} //  			

    			strBuffer_tLogRow_1.append("|");
    			


   				
	    		if(copyOfout3.participations != null) { //              
                    							
       
				strBuffer_tLogRow_1.append(
				                String.valueOf(copyOfout3.participations)							
				);


							
	    		} //  			
 

                    if (globalMap.get("tLogRow_CONSOLE")!=null)
                    {
                    	consoleOut_tLogRow_1 = (java.io.PrintStream) globalMap.get("tLogRow_CONSOLE");
                    }
                    else
                    {
                    	consoleOut_tLogRow_1 = new java.io.PrintStream(new java.io.BufferedOutputStream(System.out));
                    	globalMap.put("tLogRow_CONSOLE",consoleOut_tLogRow_1);
                    }
                    consoleOut_tLogRow_1.println(strBuffer_tLogRow_1.toString());
                    consoleOut_tLogRow_1.flush();
                    nb_line_tLogRow_1++;
//////

//////                    
                    
///////////////////////    			

 
     row2 = copyOfout3;


	tos_count_tLogRow_1++;

/**
 * [tLogRow_1 main ] stop
 */
	
	/**
	 * [tLogRow_1 process_data_begin ] start
	 */

	

	
	
	currentComponent="tLogRow_1";

	

 



/**
 * [tLogRow_1 process_data_begin ] stop
 */

	
	/**
	 * [tDBOutput_1 main ] start
	 */

	

	
	
	currentComponent="tDBOutput_1";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row2"
						
						);
					}
					



        whetherReject_tDBOutput_1 = false;
                    if(row2.announce_id == null) {
pstmt_tDBOutput_1.setNull(1, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_1.setString(1, row2.announce_id);
}

                    if(row2.views == null) {
pstmt_tDBOutput_1.setNull(2, java.sql.Types.INTEGER);
} else {pstmt_tDBOutput_1.setInt(2, row2.views);
}

                    if(row2.participations == null) {
pstmt_tDBOutput_1.setNull(3, java.sql.Types.INTEGER);
} else {pstmt_tDBOutput_1.setInt(3, row2.participations);
}

			
    		pstmt_tDBOutput_1.addBatch();
    		nb_line_tDBOutput_1++;
    		  
    		  
    		  batchSizeCounter_tDBOutput_1++;
    		  
            if(!whetherReject_tDBOutput_1) {
            }
    			if ((batchSize_tDBOutput_1 > 0) && (batchSize_tDBOutput_1 <= batchSizeCounter_tDBOutput_1)) {
                try {
						int countSum_tDBOutput_1 = 0;
						    
						for(int countEach_tDBOutput_1: pstmt_tDBOutput_1.executeBatch()) {
							countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0 : countEach_tDBOutput_1);
						}
				    	rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;
				    	
				    		insertedCount_tDBOutput_1 += countSum_tDBOutput_1;
				    	
            	    	batchSizeCounter_tDBOutput_1 = 0;
                }catch (java.sql.BatchUpdateException e_tDBOutput_1){
globalMap.put("tDBOutput_1_ERROR_MESSAGE",e_tDBOutput_1.getMessage());
				    	java.sql.SQLException ne_tDBOutput_1 = e_tDBOutput_1.getNextException(),sqle_tDBOutput_1=null;
				    	String errormessage_tDBOutput_1;
						if (ne_tDBOutput_1 != null) {
							// build new exception to provide the original cause
							sqle_tDBOutput_1 = new java.sql.SQLException(e_tDBOutput_1.getMessage() + "\ncaused by: " + ne_tDBOutput_1.getMessage(), ne_tDBOutput_1.getSQLState(), ne_tDBOutput_1.getErrorCode(), ne_tDBOutput_1);
							errormessage_tDBOutput_1 = sqle_tDBOutput_1.getMessage();
						}else{
							errormessage_tDBOutput_1 = e_tDBOutput_1.getMessage();
						}
				    	
				    	int countSum_tDBOutput_1 = 0;
						for(int countEach_tDBOutput_1: e_tDBOutput_1.getUpdateCounts()) {
							countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0 : countEach_tDBOutput_1);
						}
						rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;
						
				    		insertedCount_tDBOutput_1 += countSum_tDBOutput_1;
				    	
				    	System.err.println(errormessage_tDBOutput_1);
				    	
					}
    			}
    		

 


	tos_count_tDBOutput_1++;

/**
 * [tDBOutput_1 main ] stop
 */
	
	/**
	 * [tDBOutput_1 process_data_begin ] start
	 */

	

	
	
	currentComponent="tDBOutput_1";

	

 



/**
 * [tDBOutput_1 process_data_begin ] stop
 */
	
	/**
	 * [tDBOutput_1 process_data_end ] start
	 */

	

	
	
	currentComponent="tDBOutput_1";

	

 



/**
 * [tDBOutput_1 process_data_end ] stop
 */



	
	/**
	 * [tLogRow_1 process_data_end ] start
	 */

	

	
	
	currentComponent="tLogRow_1";

	

 



/**
 * [tLogRow_1 process_data_end ] stop
 */

} // End of branch "copyOfout3"




	
	/**
	 * [tMap_8 process_data_end ] start
	 */

	

	
	
	currentComponent="tMap_8";

	

 



/**
 * [tMap_8 process_data_end ] stop
 */

} // End of branch "out3"




	
	/**
	 * [tMap_3 process_data_end ] start
	 */

	

	
	
	currentComponent="tMap_3";

	

 



/**
 * [tMap_3 process_data_end ] stop
 */



	
	/**
	 * [tMongoDBInput_1 process_data_end ] start
	 */

	

	
	
	currentComponent="tMongoDBInput_1";

	

 



/**
 * [tMongoDBInput_1 process_data_end ] stop
 */
	
	/**
	 * [tMongoDBInput_1 end ] start
	 */

	

	
	
	currentComponent="tMongoDBInput_1";

	

            }
    globalMap.put("tMongoDBInput_1_NB_LINE", nb_line_tMongoDBInput_1);
 

ok_Hash.put("tMongoDBInput_1", true);
end_Hash.put("tMongoDBInput_1", System.currentTimeMillis());




/**
 * [tMongoDBInput_1 end ] stop
 */

	
	/**
	 * [tMap_3 end ] start
	 */

	

	
	
	currentComponent="tMap_3";

	


// ###############################
// # Lookup hashes releasing
// ###############################      





				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row1");
			  	}
			  	
 

ok_Hash.put("tMap_3", true);
end_Hash.put("tMap_3", System.currentTimeMillis());




/**
 * [tMap_3 end ] stop
 */

	
	/**
	 * [tMap_8 end ] start
	 */

	

	
	
	currentComponent="tMap_8";

	


// ###############################
// # Lookup hashes releasing
// ###############################      





				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"out3");
			  	}
			  	
 

ok_Hash.put("tMap_8", true);
end_Hash.put("tMap_8", System.currentTimeMillis());




/**
 * [tMap_8 end ] stop
 */

	
	/**
	 * [tLogRow_1 end ] start
	 */

	

	
	
	currentComponent="tLogRow_1";

	


//////
//////
globalMap.put("tLogRow_1_NB_LINE",nb_line_tLogRow_1);

///////////////////////    			

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"copyOfout3");
			  	}
			  	
 

ok_Hash.put("tLogRow_1", true);
end_Hash.put("tLogRow_1", System.currentTimeMillis());




/**
 * [tLogRow_1 end ] stop
 */

	
	/**
	 * [tDBOutput_1 end ] start
	 */

	

	
	
	currentComponent="tDBOutput_1";

	



	    try {
				int countSum_tDBOutput_1 = 0;
				if (pstmt_tDBOutput_1 != null && batchSizeCounter_tDBOutput_1 > 0) {
						
					for(int countEach_tDBOutput_1: pstmt_tDBOutput_1.executeBatch()) {
						countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0 : countEach_tDBOutput_1);
					}
					rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;
						
				}
		    	
		    		insertedCount_tDBOutput_1 += countSum_tDBOutput_1;
		    	
	    }catch (java.sql.BatchUpdateException e_tDBOutput_1){
globalMap.put("tDBOutput_1_ERROR_MESSAGE",e_tDBOutput_1.getMessage());
	    	java.sql.SQLException ne_tDBOutput_1 = e_tDBOutput_1.getNextException(),sqle_tDBOutput_1=null;
	    	String errormessage_tDBOutput_1;
			if (ne_tDBOutput_1 != null) {
				// build new exception to provide the original cause
				sqle_tDBOutput_1 = new java.sql.SQLException(e_tDBOutput_1.getMessage() + "\ncaused by: " + ne_tDBOutput_1.getMessage(), ne_tDBOutput_1.getSQLState(), ne_tDBOutput_1.getErrorCode(), ne_tDBOutput_1);
				errormessage_tDBOutput_1 = sqle_tDBOutput_1.getMessage();
			}else{
				errormessage_tDBOutput_1 = e_tDBOutput_1.getMessage();
			}
	    	
	    	int countSum_tDBOutput_1 = 0;
			for(int countEach_tDBOutput_1: e_tDBOutput_1.getUpdateCounts()) {
				countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0 : countEach_tDBOutput_1);
			}
			rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;
			
	    		insertedCount_tDBOutput_1 += countSum_tDBOutput_1;
	    	
	    	System.err.println(errormessage_tDBOutput_1);
	    	
		}
	    
        if(pstmt_tDBOutput_1 != null) {
        		
            pstmt_tDBOutput_1.close();
            resourceMap.remove("pstmt_tDBOutput_1");
        }
    resourceMap.put("statementClosed_tDBOutput_1", true);

	nb_line_deleted_tDBOutput_1=nb_line_deleted_tDBOutput_1+ deletedCount_tDBOutput_1;
	nb_line_update_tDBOutput_1=nb_line_update_tDBOutput_1 + updatedCount_tDBOutput_1;
	nb_line_inserted_tDBOutput_1=nb_line_inserted_tDBOutput_1 + insertedCount_tDBOutput_1;
	nb_line_rejected_tDBOutput_1=nb_line_rejected_tDBOutput_1 + rejectedCount_tDBOutput_1;
	
        globalMap.put("tDBOutput_1_NB_LINE",nb_line_tDBOutput_1);
        globalMap.put("tDBOutput_1_NB_LINE_UPDATED",nb_line_update_tDBOutput_1);
        globalMap.put("tDBOutput_1_NB_LINE_INSERTED",nb_line_inserted_tDBOutput_1);
        globalMap.put("tDBOutput_1_NB_LINE_DELETED",nb_line_deleted_tDBOutput_1);
        globalMap.put("tDBOutput_1_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_1);
    

	


				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row2");
			  	}
			  	
 

ok_Hash.put("tDBOutput_1", true);
end_Hash.put("tDBOutput_1", System.currentTimeMillis());

				if(execStat){   
   	 				runStat.updateStatOnConnection("OnComponentOk3", 0, "ok");
				}
				tDBCommit_1Process(globalMap);



/**
 * [tDBOutput_1 end ] stop
 */












				}//end the resume

				



	
			}catch(java.lang.Exception e){	
				
				TalendException te = new TalendException(e, currentComponent, globalMap);
				
				throw te;
			}catch(java.lang.Error error){	
				
					runStat.stopThreadStat();
				
				throw error;
			}finally{
				
				try{
					
	
	/**
	 * [tMongoDBInput_1 finally ] start
	 */

	

	
	
	currentComponent="tMongoDBInput_1";

	

 



/**
 * [tMongoDBInput_1 finally ] stop
 */

	
	/**
	 * [tMap_3 finally ] start
	 */

	

	
	
	currentComponent="tMap_3";

	

 



/**
 * [tMap_3 finally ] stop
 */

	
	/**
	 * [tMap_8 finally ] start
	 */

	

	
	
	currentComponent="tMap_8";

	

 



/**
 * [tMap_8 finally ] stop
 */

	
	/**
	 * [tLogRow_1 finally ] start
	 */

	

	
	
	currentComponent="tLogRow_1";

	

 



/**
 * [tLogRow_1 finally ] stop
 */

	
	/**
	 * [tDBOutput_1 finally ] start
	 */

	

	
	
	currentComponent="tDBOutput_1";

	



    if (resourceMap.get("statementClosed_tDBOutput_1") == null) {
                java.sql.PreparedStatement pstmtToClose_tDBOutput_1 = null;
                if ((pstmtToClose_tDBOutput_1 = (java.sql.PreparedStatement) resourceMap.remove("pstmt_tDBOutput_1")) != null) {
                    pstmtToClose_tDBOutput_1.close();
                }
    }
 



/**
 * [tDBOutput_1 finally ] stop
 */












				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tMongoDBInput_1_SUBPROCESS_STATE", 1);
	}
	

public void tDBCommit_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tDBCommit_1_SUBPROCESS_STATE", 0);

 final boolean execStat = this.execStat;
	
		String iterateId = "";
	
	
	String currentComponent = "";
	java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

	try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { //start the resume
				globalResumeTicket = true;





	
	/**
	 * [tDBCommit_1 begin ] start
	 */

	

	
		
		ok_Hash.put("tDBCommit_1", false);
		start_Hash.put("tDBCommit_1", System.currentTimeMillis());
		
	
	currentComponent="tDBCommit_1";

	
		int tos_count_tDBCommit_1 = 0;
		

 



/**
 * [tDBCommit_1 begin ] stop
 */
	
	/**
	 * [tDBCommit_1 main ] start
	 */

	

	
	
	currentComponent="tDBCommit_1";

	

	java.sql.Connection conn_tDBCommit_1 = (java.sql.Connection)globalMap.get("conn_tDBConnection_1");
	if(conn_tDBCommit_1 != null && !conn_tDBCommit_1.isClosed())
	{
	
			
			conn_tDBCommit_1.commit();
			
	
	}

 


	tos_count_tDBCommit_1++;

/**
 * [tDBCommit_1 main ] stop
 */
	
	/**
	 * [tDBCommit_1 process_data_begin ] start
	 */

	

	
	
	currentComponent="tDBCommit_1";

	

 



/**
 * [tDBCommit_1 process_data_begin ] stop
 */
	
	/**
	 * [tDBCommit_1 process_data_end ] start
	 */

	

	
	
	currentComponent="tDBCommit_1";

	

 



/**
 * [tDBCommit_1 process_data_end ] stop
 */
	
	/**
	 * [tDBCommit_1 end ] start
	 */

	

	
	
	currentComponent="tDBCommit_1";

	

 

ok_Hash.put("tDBCommit_1", true);
end_Hash.put("tDBCommit_1", System.currentTimeMillis());




/**
 * [tDBCommit_1 end ] stop
 */
				}//end the resume

				



	
			}catch(java.lang.Exception e){	
				
				TalendException te = new TalendException(e, currentComponent, globalMap);
				
				throw te;
			}catch(java.lang.Error error){	
				
					runStat.stopThreadStat();
				
				throw error;
			}finally{
				
				try{
					
	
	/**
	 * [tDBCommit_1 finally ] start
	 */

	

	
	
	currentComponent="tDBCommit_1";

	

 



/**
 * [tDBCommit_1 finally ] stop
 */
				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tDBCommit_1_SUBPROCESS_STATE", 1);
	}
	


public static class out2Struct implements routines.system.IPersistableRow<out2Struct> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_SilverToGold = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[0];

	
			    public String announce_id;

				public String getAnnounce_id () {
					return this.announce_id;
				}
				
			    public String tags;

				public String getTags () {
					return this.tags;
				}
				



	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }

    public void readData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.announce_id = readString(dis);
					
					this.tags = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.announce_id = readString(dis);
					
					this.tags = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.announce_id,dos);
					
					// String
				
						writeString(this.tags,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.announce_id,dos);
					
					// String
				
						writeString(this.tags,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("announce_id="+announce_id);
		sb.append(",tags="+tags);
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(out2Struct other) {

		int returnValue = -1;
		
	    return returnValue;
    }


    private int checkNullsAndCompare(Object object1, Object object2) {
        int returnValue = 0;
		if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }


}

public static class out6Struct implements routines.system.IPersistableRow<out6Struct> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_SilverToGold = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[0];

	
			    public String video_id;

				public String getVideo_id () {
					return this.video_id;
				}
				
			    public String tags;

				public String getTags () {
					return this.tags;
				}
				



	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }

    public void readData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.video_id = readString(dis);
					
					this.tags = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.video_id = readString(dis);
					
					this.tags = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.video_id,dos);
					
					// String
				
						writeString(this.tags,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.video_id,dos);
					
					// String
				
						writeString(this.tags,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("video_id="+video_id);
		sb.append(",tags="+tags);
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(out6Struct other) {

		int returnValue = -1;
		
	    return returnValue;
    }


    private int checkNullsAndCompare(Object object1, Object object2) {
        int returnValue = 0;
		if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }


}

public static class out1Struct implements routines.system.IPersistableRow<out1Struct> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_SilverToGold = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[0];

	
			    public String video_id;

				public String getVideo_id () {
					return this.video_id;
				}
				
			    public String video_tags;

				public String getVideo_tags () {
					return this.video_tags;
				}
				



	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }

    public void readData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.video_id = readString(dis);
					
					this.video_tags = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.video_id = readString(dis);
					
					this.video_tags = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.video_id,dos);
					
					// String
				
						writeString(this.video_tags,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.video_id,dos);
					
					// String
				
						writeString(this.video_tags,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("video_id="+video_id);
		sb.append(",video_tags="+video_tags);
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(out1Struct other) {

		int returnValue = -1;
		
	    return returnValue;
    }


    private int checkNullsAndCompare(Object object1, Object object2) {
        int returnValue = 0;
		if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }


}

public static class row5Struct implements routines.system.IPersistableRow<row5Struct> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_SilverToGold = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[0];

	
			    public String video_id;

				public String getVideo_id () {
					return this.video_id;
				}
				
			    public String video_tags;

				public String getVideo_tags () {
					return this.video_tags;
				}
				



	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }

    public void readData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.video_id = readString(dis);
					
					this.video_tags = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.video_id = readString(dis);
					
					this.video_tags = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.video_id,dos);
					
					// String
				
						writeString(this.video_tags,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.video_id,dos);
					
					// String
				
						writeString(this.video_tags,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("video_id="+video_id);
		sb.append(",video_tags="+video_tags);
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row5Struct other) {

		int returnValue = -1;
		
	    return returnValue;
    }


    private int checkNullsAndCompare(Object object1, Object object2) {
        int returnValue = 0;
		if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }


}

public static class row4Struct implements routines.system.IPersistableRow<row4Struct> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_SilverToGold = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[0];

	
			    public String video_id;

				public String getVideo_id () {
					return this.video_id;
				}
				
			    public String video_tags;

				public String getVideo_tags () {
					return this.video_tags;
				}
				



	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }

    public void readData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.video_id = readString(dis);
					
					this.video_tags = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.video_id = readString(dis);
					
					this.video_tags = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.video_id,dos);
					
					// String
				
						writeString(this.video_tags,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.video_id,dos);
					
					// String
				
						writeString(this.video_tags,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("video_id="+video_id);
		sb.append(",video_tags="+video_tags);
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row4Struct other) {

		int returnValue = -1;
		
	    return returnValue;
    }


    private int checkNullsAndCompare(Object object1, Object object2) {
        int returnValue = 0;
		if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }


}

public static class after_tMongoDBInput_2Struct implements routines.system.IPersistableRow<after_tMongoDBInput_2Struct> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_SilverToGold = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[0];

	
			    public String video_id;

				public String getVideo_id () {
					return this.video_id;
				}
				
			    public String video_tags;

				public String getVideo_tags () {
					return this.video_tags;
				}
				



	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }

    public void readData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.video_id = readString(dis);
					
					this.video_tags = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.video_id = readString(dis);
					
					this.video_tags = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.video_id,dos);
					
					// String
				
						writeString(this.video_tags,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.video_id,dos);
					
					// String
				
						writeString(this.video_tags,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("video_id="+video_id);
		sb.append(",video_tags="+video_tags);
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(after_tMongoDBInput_2Struct other) {

		int returnValue = -1;
		
	    return returnValue;
    }


    private int checkNullsAndCompare(Object object1, Object object2) {
        int returnValue = 0;
		if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }


}
public void tMongoDBInput_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tMongoDBInput_2_SUBPROCESS_STATE", 0);

 final boolean execStat = this.execStat;
	
		String iterateId = "";
	
	
	String currentComponent = "";
	java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

	try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { //start the resume
				globalResumeTicket = true;


		tMongoDBInput_3Process(globalMap);

		row4Struct row4 = new row4Struct();
row5Struct row5 = new row5Struct();
out1Struct out1 = new out1Struct();
out6Struct out6 = new out6Struct();
out2Struct out2 = new out2Struct();








	
	/**
	 * [tDBOutput_3 begin ] start
	 */

	

	
		
		ok_Hash.put("tDBOutput_3", false);
		start_Hash.put("tDBOutput_3", System.currentTimeMillis());
		
	
	currentComponent="tDBOutput_3";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"out2");
					}
				
		int tos_count_tDBOutput_3 = 0;
		





String dbschema_tDBOutput_3 = null;
	dbschema_tDBOutput_3 = (String)globalMap.get("schema_" + "tDBConnection_1");
	

String tableName_tDBOutput_3 = null;
if(dbschema_tDBOutput_3 == null || dbschema_tDBOutput_3.trim().length() == 0) {
	tableName_tDBOutput_3 = ("data_gold_tags");
} else {
	tableName_tDBOutput_3 = dbschema_tDBOutput_3 + "\".\"" + ("data_gold_tags");
}


int nb_line_tDBOutput_3 = 0;
int nb_line_update_tDBOutput_3 = 0;
int nb_line_inserted_tDBOutput_3 = 0;
int nb_line_deleted_tDBOutput_3 = 0;
int nb_line_rejected_tDBOutput_3 = 0;

int deletedCount_tDBOutput_3=0;
int updatedCount_tDBOutput_3=0;
int insertedCount_tDBOutput_3=0;
int rowsToCommitCount_tDBOutput_3=0;
int rejectedCount_tDBOutput_3=0;

boolean whetherReject_tDBOutput_3 = false;

java.sql.Connection conn_tDBOutput_3 = null;
String dbUser_tDBOutput_3 = null;

	conn_tDBOutput_3 = (java.sql.Connection)globalMap.get("conn_tDBConnection_1");
	
	


   int batchSize_tDBOutput_3 = 10000;
   int batchSizeCounter_tDBOutput_3=0;

int count_tDBOutput_3=0;
            try (java.sql.Statement stmtClear_tDBOutput_3 = conn_tDBOutput_3.createStatement()) {
                stmtClear_tDBOutput_3.executeUpdate("DELETE FROM \"" + tableName_tDBOutput_3 + "\"");
            }
	    String insert_tDBOutput_3 = "INSERT INTO \"" + tableName_tDBOutput_3 + "\" (\"announce_id\",\"tags\") VALUES (?,?)";
	    
	    java.sql.PreparedStatement pstmt_tDBOutput_3 = conn_tDBOutput_3.prepareStatement(insert_tDBOutput_3);
	    resourceMap.put("pstmt_tDBOutput_3", pstmt_tDBOutput_3);
	    

 



/**
 * [tDBOutput_3 begin ] stop
 */



	
	/**
	 * [tMap_7 begin ] start
	 */

	

	
		
		ok_Hash.put("tMap_7", false);
		start_Hash.put("tMap_7", System.currentTimeMillis());
		
	
	currentComponent="tMap_7";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"out6");
					}
				
		int tos_count_tMap_7 = 0;
		




// ###############################
// # Lookup's keys initialization
// ###############################        

// ###############################
// # Vars initialization
class  Var__tMap_7__Struct  {
}
Var__tMap_7__Struct Var__tMap_7 = new Var__tMap_7__Struct();
// ###############################

// ###############################
// # Outputs initialization
out2Struct out2_tmp = new out2Struct();
// ###############################

        
        



        









 



/**
 * [tMap_7 begin ] stop
 */



	
	/**
	 * [tMap_5 begin ] start
	 */

	

	
		
		ok_Hash.put("tMap_5", false);
		start_Hash.put("tMap_5", System.currentTimeMillis());
		
	
	currentComponent="tMap_5";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"out1");
					}
				
		int tos_count_tMap_5 = 0;
		




// ###############################
// # Lookup's keys initialization
	
		org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row12Struct> tHash_Lookup_row12 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row12Struct>) 
				((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row12Struct>) 
					globalMap.get( "tHash_Lookup_row12" ))
					;					
					
	

row12Struct row12HashKey = new row12Struct();
row12Struct row12Default = new row12Struct();
// ###############################        

// ###############################
// # Vars initialization
class  Var__tMap_5__Struct  {
}
Var__tMap_5__Struct Var__tMap_5 = new Var__tMap_5__Struct();
// ###############################

// ###############################
// # Outputs initialization
out6Struct out6_tmp = new out6Struct();
// ###############################

        
        



        









 



/**
 * [tMap_5 begin ] stop
 */



	
	/**
	 * [tMap_1 begin ] start
	 */

	

	
		
		ok_Hash.put("tMap_1", false);
		start_Hash.put("tMap_1", System.currentTimeMillis());
		
	
	currentComponent="tMap_1";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row5");
					}
				
		int tos_count_tMap_1 = 0;
		




// ###############################
// # Lookup's keys initialization
// ###############################        

// ###############################
// # Vars initialization
class  Var__tMap_1__Struct  {
}
Var__tMap_1__Struct Var__tMap_1 = new Var__tMap_1__Struct();
// ###############################

// ###############################
// # Outputs initialization
out1Struct out1_tmp = new out1Struct();
// ###############################

        
        



        









 



/**
 * [tMap_1 begin ] stop
 */



	
	/**
	 * [tNormalize_1 begin ] start
	 */

	

	
		
		ok_Hash.put("tNormalize_1", false);
		start_Hash.put("tNormalize_1", System.currentTimeMillis());
		
	
	currentComponent="tNormalize_1";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row4");
					}
				
		int tos_count_tNormalize_1 = 0;
		

	int lastNoEmptyIndex_tNormalize_1 = 0;
    int nb_line_tNormalize_1 = 0;
        String tmp_tNormalize_1 = null;
        StringBuilder currentRecord_tNormalize_1 = null;
        String [] normalizeRecord_tNormalize_1 = null;
        java.util.Set<String> recordSet_tNormalize_1 = new java.util.HashSet<String>();
        
				if ( ((String)",").length() == 0 ){
					throw new IllegalArgumentException("Field Separator must be assigned a char."); 
				}
				

 



/**
 * [tNormalize_1 begin ] stop
 */



	
	/**
	 * [tMongoDBInput_2 begin ] start
	 */

	

	
		
		ok_Hash.put("tMongoDBInput_2", false);
		start_Hash.put("tMongoDBInput_2", System.currentTimeMillis());
		
	
	currentComponent="tMongoDBInput_2";

	
		int tos_count_tMongoDBInput_2 = 0;
		


	

final String applicationName_tMongoDBInput_2 = "Talend";

	int nb_line_tMongoDBInput_2 = 0;
	


    // Declarations
    com.mongodb.client.MongoClient mongo_tMongoDBInput_2=null;
    com.mongodb.client.MongoDatabase db_tMongoDBInput_2=null;

        mongo_tMongoDBInput_2=(com.mongodb.client.MongoClient)globalMap.get("mongo_tMongoDBConnection_1");
        db_tMongoDBInput_2 = (com.mongodb.client.MongoDatabase)globalMap.get("db_tMongoDBConnection_1");


				
					com.mongodb.client.MongoCollection<org.bson.Document> coll_tMongoDBInput_2 = db_tMongoDBInput_2.getCollection("data_silver_tags");
				
				
				
				try{
					// Add warning if an index is not in the query.
					boolean needIndexWarning = true;
					String indexList = "";
					java.lang.StringBuilder sb_tMongoDBInput_2 = new java.lang.StringBuilder();
	                
	                    for (com.mongodb.DBObject index: coll_tMongoDBInput_2.listIndexes(com.mongodb.DBObject.class)) {
	                 
	                        for (String key: ((com.mongodb.DBObject)index.get("key")).keySet()) {
	                            // The regexp is:
	                            // - contain the db DBcolumnName between two backslashed quotes
	                            // - is followed at some point by a colon
	                            // - there is no comma between the the DBcolumnName and the colon
	                            if  (("{}").matches(".*" + key.replace("*","\\*") + "[^,]*:.*")) {
	                                // We have an index, do not print error message
	                                needIndexWarning = false;
	                            } else {
	                                // This index is not in the query, add it into the indexList
	                                sb_tMongoDBInput_2.append(", ").append(key);
	                            }
	                        }
	                        indexList = sb_tMongoDBInput_2.toString();
	                    }
	                if ((!"".equals(indexList)) && (needIndexWarning)) {
	                    
	                        System.err.println("tMongoDBInput_2 - The query does not contain any reference an index.  [" + indexList.substring(1) + " ]");
	                        
	                }
	            }catch(com.mongodb.MongoException e){
	            	// caught an exception after issuing the getIndexInfo()
	            	// don't fail the whole job
	            	// maybe due to authorization
	            }

						org.bson.Document myQuery_tMongoDBInput_2 = org.bson.Document.parse("{}");
						
							com.mongodb.client.FindIterable<org.bson.Document> findIterable_tMongoDBInput_2 = coll_tMongoDBInput_2.find(myQuery_tMongoDBInput_2).noCursorTimeout(false);
							


				
				class DBObjectInputUtil_tMongoDBInput_2{
					// Get the node value in embedded document, 
					//If have no embedded document get root document node.
					
					public Object getValue(String parentNode,String currentName,org.bson.Document dbObject){
						Object value=null;
						if(dbObject==null){
							return null;
						}
						if (parentNode == null || "".equals(parentNode)) {
						    if ("*".equals(currentName)) {
						        value = dbObject;
						    } else if (dbObject.get(currentName)!=null){
								value=dbObject.get(currentName);
							}
						}else{
							String objNames[] = parentNode.split("\\.");
							org.bson.Document currentObj=dbObject;
							for(int i=0;i<objNames.length;i++){
								currentObj=(org.bson.Document)currentObj.get(objNames[i]);
								if(currentObj==null){
									break;
								}
							}
							if ("*".equals(currentName)) {
                                value = currentObj;
                            } else if(currentObj!=null){
								value=currentObj.get(currentName);
							}
						}
						
						    if(value instanceof org.bson.Document){
						        value = ((org.bson.Document)value).toJson();
						    }else if (value instanceof java.util.List){

						    java.util.List list = new java.util.ArrayList();
						    ((java.util.List)value).stream().forEach(e -> {
						    if(e instanceof org.bson.Document){
						        list.add(((org.bson.Document)e).toJson());
						    }else{
						        list.add(e);
						    }
						    });
						    value = list;
						    }

						
						return value;
					}
				}
				DBObjectInputUtil_tMongoDBInput_2 dbObjectInputUtil_tMongoDBInput_2=new DBObjectInputUtil_tMongoDBInput_2();
				java.util.Map<String, String> pathMap_tMongoDBInput_2=new java.util.HashMap<>();
				pathMap_tMongoDBInput_2.put("video_id","");
				pathMap_tMongoDBInput_2.put("video_tags","");

						
					com.mongodb.client.MongoCursor<org.bson.Document> cursor_tMongoDBInput_2 = findIterable_tMongoDBInput_2.iterator();
						
				while (cursor_tMongoDBInput_2.hasNext()){
				org.bson.Document o_tMongoDBInput_2 = cursor_tMongoDBInput_2.next();
				nb_line_tMongoDBInput_2++;
				Object valueObj_tMongoDBInput_2=null;
                    valueObj_tMongoDBInput_2=dbObjectInputUtil_tMongoDBInput_2.getValue(pathMap_tMongoDBInput_2.get("video_id"),"video_id",o_tMongoDBInput_2);
					
				row4.video_id = valueObj_tMongoDBInput_2==null ? null : valueObj_tMongoDBInput_2.toString();
				
                    valueObj_tMongoDBInput_2=dbObjectInputUtil_tMongoDBInput_2.getValue(pathMap_tMongoDBInput_2.get("video_tags"),"video_tags",o_tMongoDBInput_2);
					
				row4.video_tags = valueObj_tMongoDBInput_2==null ? null : valueObj_tMongoDBInput_2.toString();
				


 



/**
 * [tMongoDBInput_2 begin ] stop
 */
	
	/**
	 * [tMongoDBInput_2 main ] start
	 */

	

	
	
	currentComponent="tMongoDBInput_2";

	

 


	tos_count_tMongoDBInput_2++;

/**
 * [tMongoDBInput_2 main ] stop
 */
	
	/**
	 * [tMongoDBInput_2 process_data_begin ] start
	 */

	

	
	
	currentComponent="tMongoDBInput_2";

	

 



/**
 * [tMongoDBInput_2 process_data_begin ] stop
 */

	
	/**
	 * [tNormalize_1 main ] start
	 */

	

	
	
	currentComponent="tNormalize_1";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row4"
						
						);
					}
					

            normalizeRecord_tNormalize_1 = new String[1];
            if(row4.video_tags != null) {
				if("".equals(row4.video_tags)){
		        	normalizeRecord_tNormalize_1[0] = "";
		        }else{
	                
	                		normalizeRecord_tNormalize_1 = row4.video_tags.split(",",-1);
	                        
                }           
            }
             		lastNoEmptyIndex_tNormalize_1=normalizeRecord_tNormalize_1.length;
	             	
            	
            	for(int i_tNormalize_1 = 0 ; i_tNormalize_1 < lastNoEmptyIndex_tNormalize_1 ; i_tNormalize_1++) {
		  			
	                currentRecord_tNormalize_1 = new StringBuilder();
	                nb_line_tNormalize_1++;               
	                
	                        	row5.video_id = row4.video_id;
	                        
	                        	row5.video_tags = normalizeRecord_tNormalize_1[i_tNormalize_1];
		                    

 


	tos_count_tNormalize_1++;

/**
 * [tNormalize_1 main ] stop
 */
	
	/**
	 * [tNormalize_1 process_data_begin ] start
	 */

	

	
	
	currentComponent="tNormalize_1";

	

 



/**
 * [tNormalize_1 process_data_begin ] stop
 */

	
	/**
	 * [tMap_1 main ] start
	 */

	

	
	
	currentComponent="tMap_1";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row5"
						
						);
					}
					

		
		
		boolean hasCasePrimitiveKeyWithNull_tMap_1 = false;
		

        // ###############################
        // # Input tables (lookups)
		  boolean rejectedInnerJoin_tMap_1 = false;
		  boolean mainRowRejected_tMap_1 = false;
            				    								  
		// ###############################
        { // start of Var scope
        
	        // ###############################
        	// # Vars tables
        
Var__tMap_1__Struct Var = Var__tMap_1;// ###############################
        // ###############################
        // # Output tables

out1 = null;


// # Output table : 'out1'
out1_tmp.video_id = row5.video_id ;
out1_tmp.video_tags = row5.video_tags.trim();
out1 = out1_tmp;
// ###############################

} // end of Var scope

rejectedInnerJoin_tMap_1 = false;










 


	tos_count_tMap_1++;

/**
 * [tMap_1 main ] stop
 */
	
	/**
	 * [tMap_1 process_data_begin ] start
	 */

	

	
	
	currentComponent="tMap_1";

	

 



/**
 * [tMap_1 process_data_begin ] stop
 */
// Start of branch "out1"
if(out1 != null) { 



	
	/**
	 * [tMap_5 main ] start
	 */

	

	
	
	currentComponent="tMap_5";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"out1"
						
						);
					}
					

		
		
		boolean hasCasePrimitiveKeyWithNull_tMap_5 = false;
		

        // ###############################
        // # Input tables (lookups)
		  boolean rejectedInnerJoin_tMap_5 = false;
		  boolean mainRowRejected_tMap_5 = false;
            				    								  
		

				///////////////////////////////////////////////
				// Starting Lookup Table "row12" 
				///////////////////////////////////////////////


				
				
                            
 					    boolean forceLooprow12 = false;
       		  	    	
       		  	    	
 							row12Struct row12ObjectFromLookup = null;
                          
		           		  	if(!rejectedInnerJoin_tMap_5) { // G_TM_M_020

								
								hasCasePrimitiveKeyWithNull_tMap_5 = false;
								
                        		    		    row12HashKey.tags = out1.video_tags ;
                        		    		

								
		                        	row12HashKey.hashCodeDirty = true;
                        		
	  					
	  							
			  					
			  					
	  					
		  							tHash_Lookup_row12.lookup( row12HashKey );

	  							

	  							

 								
		  				
	  								
						
									
  									  		
 								



							} // G_TM_M_020
			           		  	  
							
				           		if(tHash_Lookup_row12 != null && tHash_Lookup_row12.getCount(row12HashKey) > 1) { // G 071
			  							
			  						
									 		
									//System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row12' and it contains more one result from keys :  row12.tags = '" + row12HashKey.tags + "'");
								} // G 071
							

							row12Struct row12 = null;
                    		  	 
							   
                    		  	 
	       		  	    	row12Struct fromLookup_row12 = null;
							row12 = row12Default;
										 
							
								 
							
							
								if (tHash_Lookup_row12 !=null && tHash_Lookup_row12.hasNext()) { // G 099
								
							
								
								fromLookup_row12 = tHash_Lookup_row12.next();

							
							
								} // G 099
							
							

							if(fromLookup_row12 != null) {
								row12 = fromLookup_row12;
							}
							
							
							
			  							
								
	                    		  	
		                    
	            	
	            	
	            // ###############################
        { // start of Var scope
        
	        // ###############################
        	// # Vars tables
        
Var__tMap_5__Struct Var = Var__tMap_5;// ###############################
        // ###############################
        // # Output tables

out6 = null;


// # Output table : 'out6'
// # Filter conditions 
if( 

row12.tags  != null

 ) {
out6_tmp.video_id = out1.video_id ;
out6_tmp.tags = row12.name ;
out6 = out6_tmp;
} // closing filter/reject
// ###############################

} // end of Var scope

rejectedInnerJoin_tMap_5 = false;










 


	tos_count_tMap_5++;

/**
 * [tMap_5 main ] stop
 */
	
	/**
	 * [tMap_5 process_data_begin ] start
	 */

	

	
	
	currentComponent="tMap_5";

	

 



/**
 * [tMap_5 process_data_begin ] stop
 */
// Start of branch "out6"
if(out6 != null) { 



	
	/**
	 * [tMap_7 main ] start
	 */

	

	
	
	currentComponent="tMap_7";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"out6"
						
						);
					}
					

		
		
		boolean hasCasePrimitiveKeyWithNull_tMap_7 = false;
		

        // ###############################
        // # Input tables (lookups)
		  boolean rejectedInnerJoin_tMap_7 = false;
		  boolean mainRowRejected_tMap_7 = false;
            				    								  
		// ###############################
        { // start of Var scope
        
	        // ###############################
        	// # Vars tables
        
Var__tMap_7__Struct Var = Var__tMap_7;// ###############################
        // ###############################
        // # Output tables

out2 = null;


// # Output table : 'out2'
// # Filter conditions 
if( 

out6.tags != null

 ) {
out2_tmp.announce_id = out6.video_id ;
out2_tmp.tags = out6.tags ;
out2 = out2_tmp;
} // closing filter/reject
// ###############################

} // end of Var scope

rejectedInnerJoin_tMap_7 = false;










 


	tos_count_tMap_7++;

/**
 * [tMap_7 main ] stop
 */
	
	/**
	 * [tMap_7 process_data_begin ] start
	 */

	

	
	
	currentComponent="tMap_7";

	

 



/**
 * [tMap_7 process_data_begin ] stop
 */
// Start of branch "out2"
if(out2 != null) { 



	
	/**
	 * [tDBOutput_3 main ] start
	 */

	

	
	
	currentComponent="tDBOutput_3";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"out2"
						
						);
					}
					



        whetherReject_tDBOutput_3 = false;
                    if(out2.announce_id == null) {
pstmt_tDBOutput_3.setNull(1, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_3.setString(1, out2.announce_id);
}

                    if(out2.tags == null) {
pstmt_tDBOutput_3.setNull(2, java.sql.Types.VARCHAR);
} else {pstmt_tDBOutput_3.setString(2, out2.tags);
}

			
    		pstmt_tDBOutput_3.addBatch();
    		nb_line_tDBOutput_3++;
    		  
    		  
    		  batchSizeCounter_tDBOutput_3++;
    		  
            if(!whetherReject_tDBOutput_3) {
            }
    			if ((batchSize_tDBOutput_3 > 0) && (batchSize_tDBOutput_3 <= batchSizeCounter_tDBOutput_3)) {
                try {
						int countSum_tDBOutput_3 = 0;
						    
						for(int countEach_tDBOutput_3: pstmt_tDBOutput_3.executeBatch()) {
							countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0 : countEach_tDBOutput_3);
						}
				    	rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;
				    	
				    		insertedCount_tDBOutput_3 += countSum_tDBOutput_3;
				    	
            	    	batchSizeCounter_tDBOutput_3 = 0;
                }catch (java.sql.BatchUpdateException e_tDBOutput_3){
globalMap.put("tDBOutput_3_ERROR_MESSAGE",e_tDBOutput_3.getMessage());
				    	java.sql.SQLException ne_tDBOutput_3 = e_tDBOutput_3.getNextException(),sqle_tDBOutput_3=null;
				    	String errormessage_tDBOutput_3;
						if (ne_tDBOutput_3 != null) {
							// build new exception to provide the original cause
							sqle_tDBOutput_3 = new java.sql.SQLException(e_tDBOutput_3.getMessage() + "\ncaused by: " + ne_tDBOutput_3.getMessage(), ne_tDBOutput_3.getSQLState(), ne_tDBOutput_3.getErrorCode(), ne_tDBOutput_3);
							errormessage_tDBOutput_3 = sqle_tDBOutput_3.getMessage();
						}else{
							errormessage_tDBOutput_3 = e_tDBOutput_3.getMessage();
						}
				    	
				    	int countSum_tDBOutput_3 = 0;
						for(int countEach_tDBOutput_3: e_tDBOutput_3.getUpdateCounts()) {
							countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0 : countEach_tDBOutput_3);
						}
						rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;
						
				    		insertedCount_tDBOutput_3 += countSum_tDBOutput_3;
				    	
				    	System.err.println(errormessage_tDBOutput_3);
				    	
					}
    			}
    		

 


	tos_count_tDBOutput_3++;

/**
 * [tDBOutput_3 main ] stop
 */
	
	/**
	 * [tDBOutput_3 process_data_begin ] start
	 */

	

	
	
	currentComponent="tDBOutput_3";

	

 



/**
 * [tDBOutput_3 process_data_begin ] stop
 */
	
	/**
	 * [tDBOutput_3 process_data_end ] start
	 */

	

	
	
	currentComponent="tDBOutput_3";

	

 



/**
 * [tDBOutput_3 process_data_end ] stop
 */

} // End of branch "out2"




	
	/**
	 * [tMap_7 process_data_end ] start
	 */

	

	
	
	currentComponent="tMap_7";

	

 



/**
 * [tMap_7 process_data_end ] stop
 */

} // End of branch "out6"




	
	/**
	 * [tMap_5 process_data_end ] start
	 */

	

	
	
	currentComponent="tMap_5";

	

 



/**
 * [tMap_5 process_data_end ] stop
 */

} // End of branch "out1"




	
	/**
	 * [tMap_1 process_data_end ] start
	 */

	

	
	
	currentComponent="tMap_1";

	

 



/**
 * [tMap_1 process_data_end ] stop
 */
		// end for
	}



	
	/**
	 * [tNormalize_1 process_data_end ] start
	 */

	

	
	
	currentComponent="tNormalize_1";

	

 



/**
 * [tNormalize_1 process_data_end ] stop
 */



	
	/**
	 * [tMongoDBInput_2 process_data_end ] start
	 */

	

	
	
	currentComponent="tMongoDBInput_2";

	

 



/**
 * [tMongoDBInput_2 process_data_end ] stop
 */
	
	/**
	 * [tMongoDBInput_2 end ] start
	 */

	

	
	
	currentComponent="tMongoDBInput_2";

	

            }
    globalMap.put("tMongoDBInput_2_NB_LINE", nb_line_tMongoDBInput_2);
 

ok_Hash.put("tMongoDBInput_2", true);
end_Hash.put("tMongoDBInput_2", System.currentTimeMillis());




/**
 * [tMongoDBInput_2 end ] stop
 */

	
	/**
	 * [tNormalize_1 end ] start
	 */

	

	
	
	currentComponent="tNormalize_1";

	

    globalMap.put("tNormalize_1_NB_LINE", nb_line_tNormalize_1);
				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row4");
			  	}
			  	
 

ok_Hash.put("tNormalize_1", true);
end_Hash.put("tNormalize_1", System.currentTimeMillis());




/**
 * [tNormalize_1 end ] stop
 */

	
	/**
	 * [tMap_1 end ] start
	 */

	

	
	
	currentComponent="tMap_1";

	


// ###############################
// # Lookup hashes releasing
// ###############################      





				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row5");
			  	}
			  	
 

ok_Hash.put("tMap_1", true);
end_Hash.put("tMap_1", System.currentTimeMillis());




/**
 * [tMap_1 end ] stop
 */

	
	/**
	 * [tMap_5 end ] start
	 */

	

	
	
	currentComponent="tMap_5";

	


// ###############################
// # Lookup hashes releasing
					if(tHash_Lookup_row12 != null) {
						tHash_Lookup_row12.endGet();
					}
					globalMap.remove( "tHash_Lookup_row12" );

					
					
				
// ###############################      





				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"out1");
			  	}
			  	
 

ok_Hash.put("tMap_5", true);
end_Hash.put("tMap_5", System.currentTimeMillis());




/**
 * [tMap_5 end ] stop
 */

	
	/**
	 * [tMap_7 end ] start
	 */

	

	
	
	currentComponent="tMap_7";

	


// ###############################
// # Lookup hashes releasing
// ###############################      





				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"out6");
			  	}
			  	
 

ok_Hash.put("tMap_7", true);
end_Hash.put("tMap_7", System.currentTimeMillis());




/**
 * [tMap_7 end ] stop
 */

	
	/**
	 * [tDBOutput_3 end ] start
	 */

	

	
	
	currentComponent="tDBOutput_3";

	



	    try {
				int countSum_tDBOutput_3 = 0;
				if (pstmt_tDBOutput_3 != null && batchSizeCounter_tDBOutput_3 > 0) {
						
					for(int countEach_tDBOutput_3: pstmt_tDBOutput_3.executeBatch()) {
						countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0 : countEach_tDBOutput_3);
					}
					rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;
						
				}
		    	
		    		insertedCount_tDBOutput_3 += countSum_tDBOutput_3;
		    	
	    }catch (java.sql.BatchUpdateException e_tDBOutput_3){
globalMap.put("tDBOutput_3_ERROR_MESSAGE",e_tDBOutput_3.getMessage());
	    	java.sql.SQLException ne_tDBOutput_3 = e_tDBOutput_3.getNextException(),sqle_tDBOutput_3=null;
	    	String errormessage_tDBOutput_3;
			if (ne_tDBOutput_3 != null) {
				// build new exception to provide the original cause
				sqle_tDBOutput_3 = new java.sql.SQLException(e_tDBOutput_3.getMessage() + "\ncaused by: " + ne_tDBOutput_3.getMessage(), ne_tDBOutput_3.getSQLState(), ne_tDBOutput_3.getErrorCode(), ne_tDBOutput_3);
				errormessage_tDBOutput_3 = sqle_tDBOutput_3.getMessage();
			}else{
				errormessage_tDBOutput_3 = e_tDBOutput_3.getMessage();
			}
	    	
	    	int countSum_tDBOutput_3 = 0;
			for(int countEach_tDBOutput_3: e_tDBOutput_3.getUpdateCounts()) {
				countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0 : countEach_tDBOutput_3);
			}
			rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;
			
	    		insertedCount_tDBOutput_3 += countSum_tDBOutput_3;
	    	
	    	System.err.println(errormessage_tDBOutput_3);
	    	
		}
	    
        if(pstmt_tDBOutput_3 != null) {
        		
            pstmt_tDBOutput_3.close();
            resourceMap.remove("pstmt_tDBOutput_3");
        }
    resourceMap.put("statementClosed_tDBOutput_3", true);

	nb_line_deleted_tDBOutput_3=nb_line_deleted_tDBOutput_3+ deletedCount_tDBOutput_3;
	nb_line_update_tDBOutput_3=nb_line_update_tDBOutput_3 + updatedCount_tDBOutput_3;
	nb_line_inserted_tDBOutput_3=nb_line_inserted_tDBOutput_3 + insertedCount_tDBOutput_3;
	nb_line_rejected_tDBOutput_3=nb_line_rejected_tDBOutput_3 + rejectedCount_tDBOutput_3;
	
        globalMap.put("tDBOutput_3_NB_LINE",nb_line_tDBOutput_3);
        globalMap.put("tDBOutput_3_NB_LINE_UPDATED",nb_line_update_tDBOutput_3);
        globalMap.put("tDBOutput_3_NB_LINE_INSERTED",nb_line_inserted_tDBOutput_3);
        globalMap.put("tDBOutput_3_NB_LINE_DELETED",nb_line_deleted_tDBOutput_3);
        globalMap.put("tDBOutput_3_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_3);
    

	


				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"out2");
			  	}
			  	
 

ok_Hash.put("tDBOutput_3", true);
end_Hash.put("tDBOutput_3", System.currentTimeMillis());

				if(execStat){   
   	 				runStat.updateStatOnConnection("OnComponentOk4", 0, "ok");
				}
				tDBCommit_2Process(globalMap);



/**
 * [tDBOutput_3 end ] stop
 */















				}//end the resume

				



	
			}catch(java.lang.Exception e){	
				
				TalendException te = new TalendException(e, currentComponent, globalMap);
				
				throw te;
			}catch(java.lang.Error error){	
				
					runStat.stopThreadStat();
				
				throw error;
			}finally{
				
					     			//free memory for "tMap_5"
					     			globalMap.remove("tHash_Lookup_row12"); 
				     			
				try{
					
	
	/**
	 * [tMongoDBInput_2 finally ] start
	 */

	

	
	
	currentComponent="tMongoDBInput_2";

	

 



/**
 * [tMongoDBInput_2 finally ] stop
 */

	
	/**
	 * [tNormalize_1 finally ] start
	 */

	

	
	
	currentComponent="tNormalize_1";

	

 



/**
 * [tNormalize_1 finally ] stop
 */

	
	/**
	 * [tMap_1 finally ] start
	 */

	

	
	
	currentComponent="tMap_1";

	

 



/**
 * [tMap_1 finally ] stop
 */

	
	/**
	 * [tMap_5 finally ] start
	 */

	

	
	
	currentComponent="tMap_5";

	

 



/**
 * [tMap_5 finally ] stop
 */

	
	/**
	 * [tMap_7 finally ] start
	 */

	

	
	
	currentComponent="tMap_7";

	

 



/**
 * [tMap_7 finally ] stop
 */

	
	/**
	 * [tDBOutput_3 finally ] start
	 */

	

	
	
	currentComponent="tDBOutput_3";

	



    if (resourceMap.get("statementClosed_tDBOutput_3") == null) {
                java.sql.PreparedStatement pstmtToClose_tDBOutput_3 = null;
                if ((pstmtToClose_tDBOutput_3 = (java.sql.PreparedStatement) resourceMap.remove("pstmt_tDBOutput_3")) != null) {
                    pstmtToClose_tDBOutput_3.close();
                }
    }
 



/**
 * [tDBOutput_3 finally ] stop
 */















				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tMongoDBInput_2_SUBPROCESS_STATE", 1);
	}
	

public void tDBCommit_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tDBCommit_2_SUBPROCESS_STATE", 0);

 final boolean execStat = this.execStat;
	
		String iterateId = "";
	
	
	String currentComponent = "";
	java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

	try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { //start the resume
				globalResumeTicket = true;





	
	/**
	 * [tDBCommit_2 begin ] start
	 */

	

	
		
		ok_Hash.put("tDBCommit_2", false);
		start_Hash.put("tDBCommit_2", System.currentTimeMillis());
		
	
	currentComponent="tDBCommit_2";

	
		int tos_count_tDBCommit_2 = 0;
		

 



/**
 * [tDBCommit_2 begin ] stop
 */
	
	/**
	 * [tDBCommit_2 main ] start
	 */

	

	
	
	currentComponent="tDBCommit_2";

	

	java.sql.Connection conn_tDBCommit_2 = (java.sql.Connection)globalMap.get("conn_tDBConnection_1");
	if(conn_tDBCommit_2 != null && !conn_tDBCommit_2.isClosed())
	{
	
		try{
	
			
			conn_tDBCommit_2.commit();
			
	
		}finally{
			
			conn_tDBCommit_2.close();
			
			if("com.mysql.cj.jdbc.Driver".equals((String)globalMap.get("driverClass_tDBConnection_1"))
			    && routines.system.BundleUtils.inOSGi()) {
			        Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread").
			            getMethod("checkedShutdown").invoke(null, (Object[]) null);
			}
			
	    }
	
	}

 


	tos_count_tDBCommit_2++;

/**
 * [tDBCommit_2 main ] stop
 */
	
	/**
	 * [tDBCommit_2 process_data_begin ] start
	 */

	

	
	
	currentComponent="tDBCommit_2";

	

 



/**
 * [tDBCommit_2 process_data_begin ] stop
 */
	
	/**
	 * [tDBCommit_2 process_data_end ] start
	 */

	

	
	
	currentComponent="tDBCommit_2";

	

 



/**
 * [tDBCommit_2 process_data_end ] stop
 */
	
	/**
	 * [tDBCommit_2 end ] start
	 */

	

	
	
	currentComponent="tDBCommit_2";

	

 

ok_Hash.put("tDBCommit_2", true);
end_Hash.put("tDBCommit_2", System.currentTimeMillis());




/**
 * [tDBCommit_2 end ] stop
 */
				}//end the resume

				



	
			}catch(java.lang.Exception e){	
				
				TalendException te = new TalendException(e, currentComponent, globalMap);
				
				throw te;
			}catch(java.lang.Error error){	
				
					runStat.stopThreadStat();
				
				throw error;
			}finally{
				
				try{
					
	
	/**
	 * [tDBCommit_2 finally ] start
	 */

	

	
	
	currentComponent="tDBCommit_2";

	

 



/**
 * [tDBCommit_2 finally ] stop
 */
				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tDBCommit_2_SUBPROCESS_STATE", 1);
	}
	


public static class row9Struct implements routines.system.IPersistableComparableLookupRow<row9Struct> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_SilverToGold = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[0];
	protected static final int DEFAULT_HASHCODE = 1;
    protected static final int PRIME = 31;
    protected int hashCode = DEFAULT_HASHCODE;
    public boolean hashCodeDirty = true;

    public String loopKey;



	
			    public Integer rank;

				public Integer getRank () {
					return this.rank;
				}
				
			    public String name;

				public String getName () {
					return this.name;
				}
				


	@Override
	public int hashCode() {
		if (this.hashCodeDirty) {
			final int prime = PRIME;
			int result = DEFAULT_HASHCODE;
	
						result = prime * result + ((this.rank == null) ? 0 : this.rank.hashCode());
					
    		this.hashCode = result;
    		this.hashCodeDirty = false;
		}
		return this.hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final row9Struct other = (row9Struct) obj;
		
						if (this.rank == null) {
							if (other.rank != null)
								return false;
						
						} else if (!this.rank.equals(other.rank))
						
							return false;
					

		return true;
    }

	public void copyDataTo(row9Struct other) {

		other.rank = this.rank;
	            other.name = this.name;
	            
	}

	public void copyKeysDataTo(row9Struct other) {

		other.rank = this.rank;
	            	
	}



	private Integer readInteger(ObjectInputStream dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}
	
	private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
    	}
	}
	
	private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			byte[] byteArray = new byte[length];
			dis.read(byteArray);
			strReturn = new String(byteArray, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			byte[] byteArray = new byte[length];
			unmarshaller.read(byteArray);
			strReturn = new String(byteArray, utf8Charset);
		}
		return strReturn;
	}
	
	private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
	}

	private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
	}

    public void readKeysData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
						this.rank = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
						this.rank = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeKeysData(ObjectOutputStream dos) {
        try {

		
					// Integer
				
						writeInteger(this.rank,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// Integer
				
						writeInteger(this.rank,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }



    /**
     * Fill Values data by reading ObjectInputStream.
     */
    public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
        try {

			int length = 0;
		
						this.name = readString(dis,ois);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }
    
    public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
        try {
			int length = 0;
		
						this.name = readString(dis,objectIn);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }

    /**
     * Return a byte array which represents Values data.
     */
    public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
        try {

		
						writeString(this.name, dos, oos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}

    }
    
    public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut){
                try {

		
						writeString(this.name, dos, objectOut);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}
    }


    
    public boolean supportMarshaller(){
        return true;
    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("rank="+String.valueOf(rank));
		sb.append(",name="+name);
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row9Struct other) {

		int returnValue = -1;
		
						returnValue = checkNullsAndCompare(this.rank, other.rank);
						if(returnValue != 0) {
							return returnValue;
						}

					
	    return returnValue;
    }


    private int checkNullsAndCompare(Object object1, Object object2) {
        int returnValue = 0;
		if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }


}
public void tFixedFlowInput_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tFixedFlowInput_1_SUBPROCESS_STATE", 0);

 final boolean execStat = this.execStat;
	
		String iterateId = "";
	
	
	String currentComponent = "";
	java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

	try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { //start the resume
				globalResumeTicket = true;



		row9Struct row9 = new row9Struct();




	
	/**
	 * [tAdvancedHash_row9 begin ] start
	 */

	

	
		
		ok_Hash.put("tAdvancedHash_row9", false);
		start_Hash.put("tAdvancedHash_row9", System.currentTimeMillis());
		
	
	currentComponent="tAdvancedHash_row9";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row9");
					}
				
		int tos_count_tAdvancedHash_row9 = 0;
		

			   		// connection name:row9
			   		// source node:tFixedFlowInput_1 - inputs:(after_tMongoDBInput_3) outputs:(row9,row9) | target node:tAdvancedHash_row9 - inputs:(row9) outputs:()
			   		// linked node: tMap_2 - inputs:(row9,row8) outputs:(out5)
			   
			   		org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row9 = 
			   			org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;
			   			
			   
	   			org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row9Struct> tHash_Lookup_row9 =org.talend.designer.components.lookup.memory.AdvancedMemoryLookup.
	   						<row9Struct>getLookup(matchingModeEnum_row9);
	   						   
		   	   	   globalMap.put("tHash_Lookup_row9", tHash_Lookup_row9);
		   	   	   
				
           

 



/**
 * [tAdvancedHash_row9 begin ] stop
 */



	
	/**
	 * [tFixedFlowInput_1 begin ] start
	 */

	

	
		
		ok_Hash.put("tFixedFlowInput_1", false);
		start_Hash.put("tFixedFlowInput_1", System.currentTimeMillis());
		
	
	currentComponent="tFixedFlowInput_1";

	
		int tos_count_tFixedFlowInput_1 = 0;
		


StringBuilder result_tFixedFlowInput_1 = new StringBuilder();
	result_tFixedFlowInput_1.append("MTtJbnTDqXJpZXVyDQoyO0NpbsOpbWENCjM7UGxlaW4gYWlyDQo0O1ZhY2FuY2VzDQo1O0VuZmFu");
	result_tFixedFlowInput_1.append("dHMNCjY7SmV1eCAmIEpvdWV0cw0KNztDdWlzaW5lDQo4O0phcmRpbmFnZQ0KOTtNdXPDqWUNCjEw");
	result_tFixedFlowInput_1.append("O0NoYW50L1Row6nDonRyZQ0KMTE7QXNzb2NpYXRpZg0KMTI7U29jaWFsDQoxMztFbnRyYWluZW1l");
	result_tFixedFlowInput_1.append("bnQNCjE0O0luaXRpYXRpb24NCjE1O0FydGlzYW5hdA0KMTY7RW52aXJvbm5lbWVudGFsDQoxNztC");
	result_tFixedFlowInput_1.append("cmljb2xhZ2UNCjE4O1RyYXZhaWwNCjE5O0FjY29tcGFnbmVtZW50DQoyMDtTw6luaW9ycw0KMjE7");
	result_tFixedFlowInput_1.append("QW5pbWFsZXJpZQ0KMjI7Q29tcMOpdGl0aW9uDQoyMztOZXR0b3lhZ2UNCjI0O0FkbWluaXN0cmF0");
	result_tFixedFlowInput_1.append("aWYNCjI1O0ltbW9iaWxpZXINCjI2O1bDqWhpY3VsZQ0KMjc7RWxlY3Ryb25pcXVlDQoyODtNdW5p");
	result_tFixedFlowInput_1.append("Y2lwYWwNCjI5O0Ftw6luYWdlbWVudA0KMzA7Q291cCBkZSBtYWluDQozMTtFbnNlaWduZW1lbnQ=");
String originalFileContent_tFixedFlowInput_1 = "";
try {
    originalFileContent_tFixedFlowInput_1 = new String(java.util.Base64.getMimeDecoder().decode(result_tFixedFlowInput_1.toString()), utf8Charset);
} catch (java.lang.Exception e) {
globalMap.put("tFixedFlowInput_1_ERROR_MESSAGE",e.getMessage());
    e.printStackTrace();
}

int nb_line_tFixedFlowInput_1 = 0;

for (int i_tFixedFlowInput_1 = 0 ; i_tFixedFlowInput_1 < 31 ; i_tFixedFlowInput_1++) {	

java.io.InputStream ins_tFixedFlowInput_1 = new java.io.ByteArrayInputStream(originalFileContent_tFixedFlowInput_1.getBytes(utf8Charset));        
org.talend.fileprocess.FileInputDelimited fid_tFixedFlowInput_1 = new org.talend.fileprocess.FileInputDelimited(ins_tFixedFlowInput_1, utf8Charset,";","\n",true, 0, 0, -1, -1, false);

while (fid_tFixedFlowInput_1.nextRecord()) {
	nb_line_tFixedFlowInput_1++;
			row9 = new row9Struct();

				if(0 < fid_tFixedFlowInput_1.getColumnsCountOfCurrentRow()){
					String colContent = fid_tFixedFlowInput_1.get(0);				
                    		row9.rank = (colContent == null || colContent.trim().length() == 0) ? null : ParserUtils.parseTo_Integer(colContent);			
				} else { 
					
					row9.rank = null;
				
				}
			
			

				if(1 < fid_tFixedFlowInput_1.getColumnsCountOfCurrentRow()){
					String colContent = fid_tFixedFlowInput_1.get(1);				
                    		row9.name = (colContent == null || colContent.length() == 0) ? null: colContent;			
				} else { 
					
					row9.name = null;
				
				}
			
			

 



/**
 * [tFixedFlowInput_1 begin ] stop
 */
	
	/**
	 * [tFixedFlowInput_1 main ] start
	 */

	

	
	
	currentComponent="tFixedFlowInput_1";

	

 


	tos_count_tFixedFlowInput_1++;

/**
 * [tFixedFlowInput_1 main ] stop
 */
	
	/**
	 * [tFixedFlowInput_1 process_data_begin ] start
	 */

	

	
	
	currentComponent="tFixedFlowInput_1";

	

 



/**
 * [tFixedFlowInput_1 process_data_begin ] stop
 */

	
	/**
	 * [tAdvancedHash_row9 main ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row9";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row9"
						
						);
					}
					


			   
			   

					row9Struct row9_HashRow = new row9Struct();
		   	   	   
				
				row9_HashRow.rank = row9.rank;
				
				row9_HashRow.name = row9.name;
				
			tHash_Lookup_row9.put(row9_HashRow);
			
            




 


	tos_count_tAdvancedHash_row9++;

/**
 * [tAdvancedHash_row9 main ] stop
 */
	
	/**
	 * [tAdvancedHash_row9 process_data_begin ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row9";

	

 



/**
 * [tAdvancedHash_row9 process_data_begin ] stop
 */
	
	/**
	 * [tAdvancedHash_row9 process_data_end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row9";

	

 



/**
 * [tAdvancedHash_row9 process_data_end ] stop
 */



	
	/**
	 * [tFixedFlowInput_1 process_data_end ] start
	 */

	

	
	
	currentComponent="tFixedFlowInput_1";

	

 



/**
 * [tFixedFlowInput_1 process_data_end ] stop
 */
	
	/**
	 * [tFixedFlowInput_1 end ] start
	 */

	

	
	
	currentComponent="tFixedFlowInput_1";

	

		}
	  fid_tFixedFlowInput_1.close();
	}

	globalMap.put("tFixedFlowInput_1_NB_LINE", nb_line_tFixedFlowInput_1);

 

ok_Hash.put("tFixedFlowInput_1", true);
end_Hash.put("tFixedFlowInput_1", System.currentTimeMillis());




/**
 * [tFixedFlowInput_1 end ] stop
 */

	
	/**
	 * [tAdvancedHash_row9 end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row9";

	

tHash_Lookup_row9.endPut();

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row9");
			  	}
			  	
 

ok_Hash.put("tAdvancedHash_row9", true);
end_Hash.put("tAdvancedHash_row9", System.currentTimeMillis());




/**
 * [tAdvancedHash_row9 end ] stop
 */



				}//end the resume

				



	
			}catch(java.lang.Exception e){	
				
				TalendException te = new TalendException(e, currentComponent, globalMap);
				
				throw te;
			}catch(java.lang.Error error){	
				
					runStat.stopThreadStat();
				
				throw error;
			}finally{
				
				try{
					
	
	/**
	 * [tFixedFlowInput_1 finally ] start
	 */

	

	
	
	currentComponent="tFixedFlowInput_1";

	

 



/**
 * [tFixedFlowInput_1 finally ] stop
 */

	
	/**
	 * [tAdvancedHash_row9 finally ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row9";

	

 



/**
 * [tAdvancedHash_row9 finally ] stop
 */



				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tFixedFlowInput_1_SUBPROCESS_STATE", 1);
	}
	


public static class row12Struct implements routines.system.IPersistableComparableLookupRow<row12Struct> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_SilverToGold = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[0];
	protected static final int DEFAULT_HASHCODE = 1;
    protected static final int PRIME = 31;
    protected int hashCode = DEFAULT_HASHCODE;
    public boolean hashCodeDirty = true;

    public String loopKey;



	
			    public String tags;

				public String getTags () {
					return this.tags;
				}
				
			    public String name;

				public String getName () {
					return this.name;
				}
				


	@Override
	public int hashCode() {
		if (this.hashCodeDirty) {
			final int prime = PRIME;
			int result = DEFAULT_HASHCODE;
	
						result = prime * result + ((this.tags == null) ? 0 : this.tags.hashCode());
					
    		this.hashCode = result;
    		this.hashCodeDirty = false;
		}
		return this.hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final row12Struct other = (row12Struct) obj;
		
						if (this.tags == null) {
							if (other.tags != null)
								return false;
						
						} else if (!this.tags.equals(other.tags))
						
							return false;
					

		return true;
    }

	public void copyDataTo(row12Struct other) {

		other.tags = this.tags;
	            other.name = this.name;
	            
	}

	public void copyKeysDataTo(row12Struct other) {

		other.tags = this.tags;
	            	
	}




	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }
	
	private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			byte[] byteArray = new byte[length];
			dis.read(byteArray);
			strReturn = new String(byteArray, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			byte[] byteArray = new byte[length];
			unmarshaller.read(byteArray);
			strReturn = new String(byteArray, utf8Charset);
		}
		return strReturn;
	}
	
	private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
	}

	private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
	}

    public void readKeysData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.tags = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.tags = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeKeysData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.tags,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.tags,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }



    /**
     * Fill Values data by reading ObjectInputStream.
     */
    public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
        try {

			int length = 0;
		
						this.name = readString(dis,ois);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }
    
    public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
        try {
			int length = 0;
		
						this.name = readString(dis,objectIn);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

    }

    /**
     * Return a byte array which represents Values data.
     */
    public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
        try {

		
						writeString(this.name, dos, oos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}

    }
    
    public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut){
                try {

		
						writeString(this.name, dos, objectOut);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        	}
    }


    
    public boolean supportMarshaller(){
        return true;
    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("tags="+tags);
		sb.append(",name="+name);
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row12Struct other) {

		int returnValue = -1;
		
						returnValue = checkNullsAndCompare(this.tags, other.tags);
						if(returnValue != 0) {
							return returnValue;
						}

					
	    return returnValue;
    }


    private int checkNullsAndCompare(Object object1, Object object2) {
        int returnValue = 0;
		if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }


}

public static class out5Struct implements routines.system.IPersistableRow<out5Struct> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_SilverToGold = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[0];

	
			    public String tags;

				public String getTags () {
					return this.tags;
				}
				
			    public String name;

				public String getName () {
					return this.name;
				}
				



	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }

    public void readData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.tags = readString(dis);
					
					this.name = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.tags = readString(dis);
					
					this.name = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.tags,dos);
					
					// String
				
						writeString(this.name,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.tags,dos);
					
					// String
				
						writeString(this.name,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("tags="+tags);
		sb.append(",name="+name);
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(out5Struct other) {

		int returnValue = -1;
		
	    return returnValue;
    }


    private int checkNullsAndCompare(Object object1, Object object2) {
        int returnValue = 0;
		if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }


}

public static class row8Struct implements routines.system.IPersistableRow<row8Struct> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_SilverToGold = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[0];

	
			    public String tags;

				public String getTags () {
					return this.tags;
				}
				
			    public Integer nb_occ;

				public Integer getNb_occ () {
					return this.nb_occ;
				}
				
			    public Integer rank;

				public Integer getRank () {
					return this.rank;
				}
				



	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }
	private Integer readInteger(ObjectInputStream dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}
	
	private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
    	}
	}

    public void readData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.tags = readString(dis);
					
						this.nb_occ = readInteger(dis);
					
						this.rank = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.tags = readString(dis);
					
						this.nb_occ = readInteger(dis);
					
						this.rank = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.tags,dos);
					
					// Integer
				
						writeInteger(this.nb_occ,dos);
					
					// Integer
				
						writeInteger(this.rank,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.tags,dos);
					
					// Integer
				
						writeInteger(this.nb_occ,dos);
					
					// Integer
				
						writeInteger(this.rank,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("tags="+tags);
		sb.append(",nb_occ="+String.valueOf(nb_occ));
		sb.append(",rank="+String.valueOf(rank));
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row8Struct other) {

		int returnValue = -1;
		
	    return returnValue;
    }


    private int checkNullsAndCompare(Object object1, Object object2) {
        int returnValue = 0;
		if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }


}

public static class row3Struct implements routines.system.IPersistableRow<row3Struct> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_SilverToGold = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[0];

	
			    public String tags;

				public String getTags () {
					return this.tags;
				}
				
			    public Integer nb_occ;

				public Integer getNb_occ () {
					return this.nb_occ;
				}
				
			    public Integer rank;

				public Integer getRank () {
					return this.rank;
				}
				



	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }
	private Integer readInteger(ObjectInputStream dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}
	
	private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
    	}
	}

    public void readData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.tags = readString(dis);
					
						this.nb_occ = readInteger(dis);
					
						this.rank = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.tags = readString(dis);
					
						this.nb_occ = readInteger(dis);
					
						this.rank = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.tags,dos);
					
					// Integer
				
						writeInteger(this.nb_occ,dos);
					
					// Integer
				
						writeInteger(this.rank,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.tags,dos);
					
					// Integer
				
						writeInteger(this.nb_occ,dos);
					
					// Integer
				
						writeInteger(this.rank,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("tags="+tags);
		sb.append(",nb_occ="+String.valueOf(nb_occ));
		sb.append(",rank="+String.valueOf(rank));
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row3Struct other) {

		int returnValue = -1;
		
	    return returnValue;
    }


    private int checkNullsAndCompare(Object object1, Object object2) {
        int returnValue = 0;
		if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }


}

public static class out4Struct implements routines.system.IPersistableRow<out4Struct> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_SilverToGold = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[0];

	
			    public String tags;

				public String getTags () {
					return this.tags;
				}
				
			    public Integer nb_occ;

				public Integer getNb_occ () {
					return this.nb_occ;
				}
				
			    public Integer rank;

				public Integer getRank () {
					return this.rank;
				}
				



	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }
	private Integer readInteger(ObjectInputStream dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}
	
	private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
    	}
	}

    public void readData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.tags = readString(dis);
					
						this.nb_occ = readInteger(dis);
					
						this.rank = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.tags = readString(dis);
					
						this.nb_occ = readInteger(dis);
					
						this.rank = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.tags,dos);
					
					// Integer
				
						writeInteger(this.nb_occ,dos);
					
					// Integer
				
						writeInteger(this.rank,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.tags,dos);
					
					// Integer
				
						writeInteger(this.nb_occ,dos);
					
					// Integer
				
						writeInteger(this.rank,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("tags="+tags);
		sb.append(",nb_occ="+String.valueOf(nb_occ));
		sb.append(",rank="+String.valueOf(rank));
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(out4Struct other) {

		int returnValue = -1;
		
	    return returnValue;
    }


    private int checkNullsAndCompare(Object object1, Object object2) {
        int returnValue = 0;
		if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }


}

public static class row7Struct implements routines.system.IPersistableRow<row7Struct> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_SilverToGold = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[0];

	
			    public String tags;

				public String getTags () {
					return this.tags;
				}
				
			    public Integer nb_occ;

				public Integer getNb_occ () {
					return this.nb_occ;
				}
				



	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }
	private Integer readInteger(ObjectInputStream dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}
	
	private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
    	}
	}

    public void readData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.tags = readString(dis);
					
						this.nb_occ = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.tags = readString(dis);
					
						this.nb_occ = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.tags,dos);
					
					// Integer
				
						writeInteger(this.nb_occ,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.tags,dos);
					
					// Integer
				
						writeInteger(this.nb_occ,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("tags="+tags);
		sb.append(",nb_occ="+String.valueOf(nb_occ));
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row7Struct other) {

		int returnValue = -1;
		
	    return returnValue;
    }


    private int checkNullsAndCompare(Object object1, Object object2) {
        int returnValue = 0;
		if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }


}

public static class OnRowsEndStructtSortRow_2 implements routines.system.IPersistableRow<OnRowsEndStructtSortRow_2> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_SilverToGold = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[0];

	
			    public String tags;

				public String getTags () {
					return this.tags;
				}
				
			    public Integer nb_occ;

				public Integer getNb_occ () {
					return this.nb_occ;
				}
				



	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }
	private Integer readInteger(ObjectInputStream dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}
	
	private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
    	}
	}

    public void readData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.tags = readString(dis);
					
						this.nb_occ = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.tags = readString(dis);
					
						this.nb_occ = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.tags,dos);
					
					// Integer
				
						writeInteger(this.nb_occ,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.tags,dos);
					
					// Integer
				
						writeInteger(this.nb_occ,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("tags="+tags);
		sb.append(",nb_occ="+String.valueOf(nb_occ));
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(OnRowsEndStructtSortRow_2 other) {

		int returnValue = -1;
		
	    return returnValue;
    }


    private int checkNullsAndCompare(Object object1, Object object2) {
        int returnValue = 0;
		if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }


}

public static class row6Struct implements routines.system.IPersistableRow<row6Struct> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_SilverToGold = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[0];

	
			    public String tags;

				public String getTags () {
					return this.tags;
				}
				
			    public Integer nb_occ;

				public Integer getNb_occ () {
					return this.nb_occ;
				}
				



	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }
	private Integer readInteger(ObjectInputStream dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}
	
	private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
    	}
	}

    public void readData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.tags = readString(dis);
					
						this.nb_occ = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.tags = readString(dis);
					
						this.nb_occ = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.tags,dos);
					
					// Integer
				
						writeInteger(this.nb_occ,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.tags,dos);
					
					// Integer
				
						writeInteger(this.nb_occ,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("tags="+tags);
		sb.append(",nb_occ="+String.valueOf(nb_occ));
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row6Struct other) {

		int returnValue = -1;
		
	    return returnValue;
    }


    private int checkNullsAndCompare(Object object1, Object object2) {
        int returnValue = 0;
		if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }


}

public static class OnRowsEndStructtAggregateRow_1 implements routines.system.IPersistableRow<OnRowsEndStructtAggregateRow_1> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_SilverToGold = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[0];

	
			    public String tags;

				public String getTags () {
					return this.tags;
				}
				
			    public Integer nb_occ;

				public Integer getNb_occ () {
					return this.nb_occ;
				}
				



	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }
	private Integer readInteger(ObjectInputStream dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}
	
	private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
    	}
	}

    public void readData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.tags = readString(dis);
					
						this.nb_occ = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.tags = readString(dis);
					
						this.nb_occ = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.tags,dos);
					
					// Integer
				
						writeInteger(this.nb_occ,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.tags,dos);
					
					// Integer
				
						writeInteger(this.nb_occ,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("tags="+tags);
		sb.append(",nb_occ="+String.valueOf(nb_occ));
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(OnRowsEndStructtAggregateRow_1 other) {

		int returnValue = -1;
		
	    return returnValue;
    }


    private int checkNullsAndCompare(Object object1, Object object2) {
        int returnValue = 0;
		if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }


}

public static class copyOfout2Struct implements routines.system.IPersistableRow<copyOfout2Struct> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_SilverToGold = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[0];

	
			    public String video_tags;

				public String getVideo_tags () {
					return this.video_tags;
				}
				
			    public Integer nb_occ;

				public Integer getNb_occ () {
					return this.nb_occ;
				}
				



	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }
	private Integer readInteger(ObjectInputStream dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}
	
	private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException{
		Integer intReturn;
        int length = 0;
        length = dis.readByte();
		if (length == -1) {
			intReturn = null;
		} else {
	    	intReturn = dis.readInt();
		}
		return intReturn;
	}

	private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
    	}
	}

    public void readData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.video_tags = readString(dis);
					
						this.nb_occ = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.video_tags = readString(dis);
					
						this.nb_occ = readInteger(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.video_tags,dos);
					
					// Integer
				
						writeInteger(this.nb_occ,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.video_tags,dos);
					
					// Integer
				
						writeInteger(this.nb_occ,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("video_tags="+video_tags);
		sb.append(",nb_occ="+String.valueOf(nb_occ));
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(copyOfout2Struct other) {

		int returnValue = -1;
		
	    return returnValue;
    }


    private int checkNullsAndCompare(Object object1, Object object2) {
        int returnValue = 0;
		if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }


}

public static class row11Struct implements routines.system.IPersistableRow<row11Struct> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_SilverToGold = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[0];

	
			    public String video_id;

				public String getVideo_id () {
					return this.video_id;
				}
				
			    public String video_tags;

				public String getVideo_tags () {
					return this.video_tags;
				}
				



	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }

    public void readData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.video_id = readString(dis);
					
					this.video_tags = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.video_id = readString(dis);
					
					this.video_tags = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.video_id,dos);
					
					// String
				
						writeString(this.video_tags,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.video_id,dos);
					
					// String
				
						writeString(this.video_tags,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("video_id="+video_id);
		sb.append(",video_tags="+video_tags);
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row11Struct other) {

		int returnValue = -1;
		
	    return returnValue;
    }


    private int checkNullsAndCompare(Object object1, Object object2) {
        int returnValue = 0;
		if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }


}

public static class row10Struct implements routines.system.IPersistableRow<row10Struct> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_SilverToGold = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[0];

	
			    public String video_id;

				public String getVideo_id () {
					return this.video_id;
				}
				
			    public String video_tags;

				public String getVideo_tags () {
					return this.video_tags;
				}
				



	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }

    public void readData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.video_id = readString(dis);
					
					this.video_tags = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.video_id = readString(dis);
					
					this.video_tags = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.video_id,dos);
					
					// String
				
						writeString(this.video_tags,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.video_id,dos);
					
					// String
				
						writeString(this.video_tags,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("video_id="+video_id);
		sb.append(",video_tags="+video_tags);
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(row10Struct other) {

		int returnValue = -1;
		
	    return returnValue;
    }


    private int checkNullsAndCompare(Object object1, Object object2) {
        int returnValue = 0;
		if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }


}

public static class after_tMongoDBInput_3Struct implements routines.system.IPersistableRow<after_tMongoDBInput_3Struct> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_SilverToGold = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[0];

	
			    public String video_id;

				public String getVideo_id () {
					return this.video_id;
				}
				
			    public String video_tags;

				public String getVideo_tags () {
					return this.video_tags;
				}
				



	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_SilverToGold.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_SilverToGold.length == 0) {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_SilverToGold = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_SilverToGold, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }

    public void readData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.video_id = readString(dis);
					
					this.video_tags = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_SilverToGold) {

        	try {

        		int length = 0;
		
					this.video_id = readString(dis);
					
					this.video_tags = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.video_id,dos);
					
					// String
				
						writeString(this.video_tags,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.video_id,dos);
					
					// String
				
						writeString(this.video_tags,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("video_id="+video_id);
		sb.append(",video_tags="+video_tags);
	    sb.append("]");

	    return sb.toString();
    }

    /**
     * Compare keys
     */
    public int compareTo(after_tMongoDBInput_3Struct other) {

		int returnValue = -1;
		
	    return returnValue;
    }


    private int checkNullsAndCompare(Object object1, Object object2) {
        int returnValue = 0;
		if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return returnValue;
    }

    private int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }


}
public void tMongoDBInput_3Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tMongoDBInput_3_SUBPROCESS_STATE", 0);

 final boolean execStat = this.execStat;
		String currentVirtualComponent = null;
	
		String iterateId = "";
	
	
	String currentComponent = "";
	java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

	try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { //start the resume
				globalResumeTicket = true;


		tFixedFlowInput_1Process(globalMap);

		row10Struct row10 = new row10Struct();
row11Struct row11 = new row11Struct();
copyOfout2Struct copyOfout2 = new copyOfout2Struct();
row6Struct row6 = new row6Struct();
row7Struct row7 = new row7Struct();
out4Struct out4 = new out4Struct();
out4Struct row3 = out4;
row8Struct row8 = new row8Struct();
out5Struct out5 = new out5Struct();
out5Struct row12 = out5;






	
	/**
	 * [tAggregateRow_1_AGGOUT begin ] start
	 */

	

	
		
		ok_Hash.put("tAggregateRow_1_AGGOUT", false);
		start_Hash.put("tAggregateRow_1_AGGOUT", System.currentTimeMillis());
		
	
		currentVirtualComponent = "tAggregateRow_1";
	
	currentComponent="tAggregateRow_1_AGGOUT";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"copyOfout2");
					}
				
		int tos_count_tAggregateRow_1_AGGOUT = 0;
		

// ------------ Seems it is not used

java.util.Map hashAggreg_tAggregateRow_1 = new java.util.HashMap(); 

// ------------

	class UtilClass_tAggregateRow_1 { // G_OutBegin_AggR_144

		public double sd(Double[] data) {
	        final int n = data.length;
        	if (n < 2) {
	            return Double.NaN;
        	}
        	double d1 = 0d;
        	double d2 =0d;
	        
	        for (int i = 0; i < data.length; i++) {
            	d1 += (data[i]*data[i]);
            	d2 += data[i];
        	}
        
	        return Math.sqrt((n*d1 - d2*d2)/n/(n-1));
	    }
	    
		public void checkedIADD(byte a, byte b, boolean checkTypeOverFlow, boolean checkUlp) {
		    byte r = (byte) (a + b);
		    if (checkTypeOverFlow && ((a ^ r) & (b ^ r)) < 0) {
		        throw new RuntimeException(buildOverflowMessage(String.valueOf(a), String.valueOf(b), "'short/Short'", "'byte/Byte'"));
		    }
		}
		
		public void checkedIADD(short a, short b, boolean checkTypeOverFlow, boolean checkUlp) {
		    short r = (short) (a + b);
		    if (checkTypeOverFlow && ((a ^ r) & (b ^ r)) < 0) {
		        throw new RuntimeException(buildOverflowMessage(String.valueOf(a), String.valueOf(b), "'int/Integer'", "'short/Short'"));
		    }
		}
		
		public void checkedIADD(int a, int b, boolean checkTypeOverFlow, boolean checkUlp) {
		    int r = a + b;
		    if (checkTypeOverFlow && ((a ^ r) & (b ^ r)) < 0) {
		        throw new RuntimeException(buildOverflowMessage(String.valueOf(a), String.valueOf(b), "'long/Long'", "'int/Integer'"));
		    }
		}
		
		public void checkedIADD(long a, long b, boolean checkTypeOverFlow, boolean checkUlp) {
		    long r = a + b;
		    if (checkTypeOverFlow && ((a ^ r) & (b ^ r)) < 0) {
		        throw new RuntimeException(buildOverflowMessage(String.valueOf(a), String.valueOf(b), "'BigDecimal'", "'long/Long'"));
		    }
		}
		
		public void checkedIADD(float a, float b, boolean checkTypeOverFlow, boolean checkUlp) {
		
			if(checkUlp) {
			    float minAddedValue = Math.ulp(a);
			    if (minAddedValue > Math.abs(b)) {
			        throw new RuntimeException(buildPrecisionMessage(String.valueOf(a), String.valueOf(b), "'double' or 'BigDecimal'", "'float/Float'"));
			    }
			}
			
		    if (checkTypeOverFlow && ((double) a + (double) b > (double) Float.MAX_VALUE) || ((double) a + (double) b < (double) -Float.MAX_VALUE)) {
		        throw new RuntimeException(buildOverflowMessage(String.valueOf(a), String.valueOf(b), "'double' or 'BigDecimal'", "'float/Float'"));
		    }
		}
		
		public void checkedIADD(double a, double b, boolean checkTypeOverFlow, boolean checkUlp) {
		
			if(checkUlp) {
			    double minAddedValue = Math.ulp(a);
			    if (minAddedValue > Math.abs(b)) {
			        throw new RuntimeException(buildPrecisionMessage(String.valueOf(a), String.valueOf(a), "'BigDecimal'", "'double/Double'"));
			    }
			}
		
		    if (checkTypeOverFlow && (a + b > (double) Double.MAX_VALUE) || (a + b < -Double.MAX_VALUE )) {
		        throw new RuntimeException(buildOverflowMessage(String.valueOf(a), String.valueOf(b), "'BigDecimal'", "'double/Double'"));
		    }
		}
		
		public void checkedIADD(double a, byte b, boolean checkTypeOverFlow, boolean checkUlp) {
		
		    if (checkTypeOverFlow && (a + b > (double) Double.MAX_VALUE) || (a + b < -Double.MAX_VALUE )) {
		        throw new RuntimeException(buildOverflowMessage(String.valueOf(a), String.valueOf(b), "'BigDecimal'", "'double/Double'"));
		    }
		}
		
		public void checkedIADD(double a, short b, boolean checkTypeOverFlow, boolean checkUlp) {
		
		    if (checkTypeOverFlow && (a + b > (double) Double.MAX_VALUE) || (a + b < -Double.MAX_VALUE )) {
		        throw new RuntimeException(buildOverflowMessage(String.valueOf(a), String.valueOf(b), "'BigDecimal'", "'double/Double'"));
		    }
		}
		
		public void checkedIADD(double a, int b, boolean checkTypeOverFlow, boolean checkUlp) {
		
		    if (checkTypeOverFlow && (a + b > (double) Double.MAX_VALUE) || (a + b < -Double.MAX_VALUE )) {
		        throw new RuntimeException(buildOverflowMessage(String.valueOf(a), String.valueOf(b), "'BigDecimal'", "'double/Double'"));
		    }
		}
		
		public void checkedIADD(double a, float b, boolean checkTypeOverFlow, boolean checkUlp) {
		
			if(checkUlp) {
			    double minAddedValue = Math.ulp(a);
			    if (minAddedValue > Math.abs(b)) {
			        throw new RuntimeException(buildPrecisionMessage(String.valueOf(a), String.valueOf(a), "'BigDecimal'", "'double/Double'"));
			    }
			}
		
		    if (checkTypeOverFlow && (a + b > (double) Double.MAX_VALUE) || (a + b < -Double.MAX_VALUE )) {
		        throw new RuntimeException(buildOverflowMessage(String.valueOf(a), String.valueOf(b), "'BigDecimal'", "'double/Double'"));
		    }
		}
		
		private String buildOverflowMessage(String a, String b, String advicedTypes, String originalType) {
		    return "Type overflow when adding " + b + " to " + a
		    + ", to resolve this problem, increase the precision by using "+ advicedTypes +" type in place of "+ originalType +".";
		}
		
		private String buildPrecisionMessage(String a, String b, String advicedTypes, String originalType) {
		    return "The double precision is unsufficient to add the value " + b + " to " + a
		    + ", to resolve this problem, increase the precision by using "+ advicedTypes +" type in place of "+ originalType +".";
		}

	} // G_OutBegin_AggR_144

	UtilClass_tAggregateRow_1 utilClass_tAggregateRow_1 = new UtilClass_tAggregateRow_1();

	

	class AggOperationStruct_tAggregateRow_1 { // G_OutBegin_AggR_100

		private static final int DEFAULT_HASHCODE = 1;
	    private static final int PRIME = 31;
	    private int hashCode = DEFAULT_HASHCODE;
	    public boolean hashCodeDirty = true;

    				String video_tags;int count = 0;
       			int nb_occ_clmCount = 0;
           			
        
	    @Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;
		
							result = prime * result + ((this.video_tags == null) ? 0 : this.video_tags.hashCode());
							
	    		this.hashCode = result;
	    		this.hashCodeDirty = false;		
			}
			return this.hashCode;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			final AggOperationStruct_tAggregateRow_1 other = (AggOperationStruct_tAggregateRow_1) obj;
			
							if (this.video_tags == null) {
								if (other.video_tags != null) 
									return false;
							} else if (!this.video_tags.equals(other.video_tags)) 
								return false;
						
			
			return true;
		}
  
        
	} // G_OutBegin_AggR_100

	AggOperationStruct_tAggregateRow_1 operation_result_tAggregateRow_1 = null;
	AggOperationStruct_tAggregateRow_1 operation_finder_tAggregateRow_1 = new AggOperationStruct_tAggregateRow_1();
	java.util.Map<AggOperationStruct_tAggregateRow_1,AggOperationStruct_tAggregateRow_1> hash_tAggregateRow_1 = new java.util.HashMap<AggOperationStruct_tAggregateRow_1,AggOperationStruct_tAggregateRow_1>();
	

 



/**
 * [tAggregateRow_1_AGGOUT begin ] stop
 */



	
	/**
	 * [tMap_6 begin ] start
	 */

	

	
		
		ok_Hash.put("tMap_6", false);
		start_Hash.put("tMap_6", System.currentTimeMillis());
		
	
	currentComponent="tMap_6";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row11");
					}
				
		int tos_count_tMap_6 = 0;
		




// ###############################
// # Lookup's keys initialization
// ###############################        

// ###############################
// # Vars initialization
class  Var__tMap_6__Struct  {
}
Var__tMap_6__Struct Var__tMap_6 = new Var__tMap_6__Struct();
// ###############################

// ###############################
// # Outputs initialization
copyOfout2Struct copyOfout2_tmp = new copyOfout2Struct();
// ###############################

        
        



        









 



/**
 * [tMap_6 begin ] stop
 */



	
	/**
	 * [tNormalize_2 begin ] start
	 */

	

	
		
		ok_Hash.put("tNormalize_2", false);
		start_Hash.put("tNormalize_2", System.currentTimeMillis());
		
	
	currentComponent="tNormalize_2";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row10");
					}
				
		int tos_count_tNormalize_2 = 0;
		

	int lastNoEmptyIndex_tNormalize_2 = 0;
    int nb_line_tNormalize_2 = 0;
        String tmp_tNormalize_2 = null;
        StringBuilder currentRecord_tNormalize_2 = null;
        String [] normalizeRecord_tNormalize_2 = null;
        java.util.Set<String> recordSet_tNormalize_2 = new java.util.HashSet<String>();
        
				if ( ((String)",").length() == 0 ){
					throw new IllegalArgumentException("Field Separator must be assigned a char."); 
				}
				

 



/**
 * [tNormalize_2 begin ] stop
 */



	
	/**
	 * [tMongoDBInput_3 begin ] start
	 */

	

	
		
		ok_Hash.put("tMongoDBInput_3", false);
		start_Hash.put("tMongoDBInput_3", System.currentTimeMillis());
		
	
	currentComponent="tMongoDBInput_3";

	
		int tos_count_tMongoDBInput_3 = 0;
		


	

final String applicationName_tMongoDBInput_3 = "Talend";

	int nb_line_tMongoDBInput_3 = 0;
	


    // Declarations
    com.mongodb.client.MongoClient mongo_tMongoDBInput_3=null;
    com.mongodb.client.MongoDatabase db_tMongoDBInput_3=null;

        mongo_tMongoDBInput_3=(com.mongodb.client.MongoClient)globalMap.get("mongo_tMongoDBConnection_1");
        db_tMongoDBInput_3 = (com.mongodb.client.MongoDatabase)globalMap.get("db_tMongoDBConnection_1");


				
					com.mongodb.client.MongoCollection<org.bson.Document> coll_tMongoDBInput_3 = db_tMongoDBInput_3.getCollection("data_silver_tags");
				
				
				
				try{
					// Add warning if an index is not in the query.
					boolean needIndexWarning = true;
					String indexList = "";
					java.lang.StringBuilder sb_tMongoDBInput_3 = new java.lang.StringBuilder();
	                
	                    for (com.mongodb.DBObject index: coll_tMongoDBInput_3.listIndexes(com.mongodb.DBObject.class)) {
	                 
	                        for (String key: ((com.mongodb.DBObject)index.get("key")).keySet()) {
	                            // The regexp is:
	                            // - contain the db DBcolumnName between two backslashed quotes
	                            // - is followed at some point by a colon
	                            // - there is no comma between the the DBcolumnName and the colon
	                            if  (("{}").matches(".*" + key.replace("*","\\*") + "[^,]*:.*")) {
	                                // We have an index, do not print error message
	                                needIndexWarning = false;
	                            } else {
	                                // This index is not in the query, add it into the indexList
	                                sb_tMongoDBInput_3.append(", ").append(key);
	                            }
	                        }
	                        indexList = sb_tMongoDBInput_3.toString();
	                    }
	                if ((!"".equals(indexList)) && (needIndexWarning)) {
	                    
	                        System.err.println("tMongoDBInput_3 - The query does not contain any reference an index.  [" + indexList.substring(1) + " ]");
	                        
	                }
	            }catch(com.mongodb.MongoException e){
	            	// caught an exception after issuing the getIndexInfo()
	            	// don't fail the whole job
	            	// maybe due to authorization
	            }

						org.bson.Document myQuery_tMongoDBInput_3 = org.bson.Document.parse("{}");
						
							com.mongodb.client.FindIterable<org.bson.Document> findIterable_tMongoDBInput_3 = coll_tMongoDBInput_3.find(myQuery_tMongoDBInput_3).noCursorTimeout(false);
							


				
				class DBObjectInputUtil_tMongoDBInput_3{
					// Get the node value in embedded document, 
					//If have no embedded document get root document node.
					
					public Object getValue(String parentNode,String currentName,org.bson.Document dbObject){
						Object value=null;
						if(dbObject==null){
							return null;
						}
						if (parentNode == null || "".equals(parentNode)) {
						    if ("*".equals(currentName)) {
						        value = dbObject;
						    } else if (dbObject.get(currentName)!=null){
								value=dbObject.get(currentName);
							}
						}else{
							String objNames[] = parentNode.split("\\.");
							org.bson.Document currentObj=dbObject;
							for(int i=0;i<objNames.length;i++){
								currentObj=(org.bson.Document)currentObj.get(objNames[i]);
								if(currentObj==null){
									break;
								}
							}
							if ("*".equals(currentName)) {
                                value = currentObj;
                            } else if(currentObj!=null){
								value=currentObj.get(currentName);
							}
						}
						
						    if(value instanceof org.bson.Document){
						        value = ((org.bson.Document)value).toJson();
						    }else if (value instanceof java.util.List){

						    java.util.List list = new java.util.ArrayList();
						    ((java.util.List)value).stream().forEach(e -> {
						    if(e instanceof org.bson.Document){
						        list.add(((org.bson.Document)e).toJson());
						    }else{
						        list.add(e);
						    }
						    });
						    value = list;
						    }

						
						return value;
					}
				}
				DBObjectInputUtil_tMongoDBInput_3 dbObjectInputUtil_tMongoDBInput_3=new DBObjectInputUtil_tMongoDBInput_3();
				java.util.Map<String, String> pathMap_tMongoDBInput_3=new java.util.HashMap<>();
				pathMap_tMongoDBInput_3.put("video_id","");
				pathMap_tMongoDBInput_3.put("video_tags","");

						
					com.mongodb.client.MongoCursor<org.bson.Document> cursor_tMongoDBInput_3 = findIterable_tMongoDBInput_3.iterator();
						
				while (cursor_tMongoDBInput_3.hasNext()){
				org.bson.Document o_tMongoDBInput_3 = cursor_tMongoDBInput_3.next();
				nb_line_tMongoDBInput_3++;
				Object valueObj_tMongoDBInput_3=null;
                    valueObj_tMongoDBInput_3=dbObjectInputUtil_tMongoDBInput_3.getValue(pathMap_tMongoDBInput_3.get("video_id"),"video_id",o_tMongoDBInput_3);
					
				row10.video_id = valueObj_tMongoDBInput_3==null ? null : valueObj_tMongoDBInput_3.toString();
				
                    valueObj_tMongoDBInput_3=dbObjectInputUtil_tMongoDBInput_3.getValue(pathMap_tMongoDBInput_3.get("video_tags"),"video_tags",o_tMongoDBInput_3);
					
				row10.video_tags = valueObj_tMongoDBInput_3==null ? null : valueObj_tMongoDBInput_3.toString();
				


 



/**
 * [tMongoDBInput_3 begin ] stop
 */
	
	/**
	 * [tMongoDBInput_3 main ] start
	 */

	

	
	
	currentComponent="tMongoDBInput_3";

	

 


	tos_count_tMongoDBInput_3++;

/**
 * [tMongoDBInput_3 main ] stop
 */
	
	/**
	 * [tMongoDBInput_3 process_data_begin ] start
	 */

	

	
	
	currentComponent="tMongoDBInput_3";

	

 



/**
 * [tMongoDBInput_3 process_data_begin ] stop
 */

	
	/**
	 * [tNormalize_2 main ] start
	 */

	

	
	
	currentComponent="tNormalize_2";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row10"
						
						);
					}
					

            normalizeRecord_tNormalize_2 = new String[1];
            if(row10.video_tags != null) {
				if("".equals(row10.video_tags)){
		        	normalizeRecord_tNormalize_2[0] = "";
		        }else{
	                
	                		normalizeRecord_tNormalize_2 = row10.video_tags.split(",",-1);
	                        
                }           
            }
             		lastNoEmptyIndex_tNormalize_2=normalizeRecord_tNormalize_2.length;
	             	
            	
            	for(int i_tNormalize_2 = 0 ; i_tNormalize_2 < lastNoEmptyIndex_tNormalize_2 ; i_tNormalize_2++) {
		  			
	                currentRecord_tNormalize_2 = new StringBuilder();
	                nb_line_tNormalize_2++;               
	                
	                        	row11.video_id = row10.video_id;
	                        
	                        	row11.video_tags = normalizeRecord_tNormalize_2[i_tNormalize_2];
		                    

 


	tos_count_tNormalize_2++;

/**
 * [tNormalize_2 main ] stop
 */
	
	/**
	 * [tNormalize_2 process_data_begin ] start
	 */

	

	
	
	currentComponent="tNormalize_2";

	

 



/**
 * [tNormalize_2 process_data_begin ] stop
 */

	
	/**
	 * [tMap_6 main ] start
	 */

	

	
	
	currentComponent="tMap_6";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row11"
						
						);
					}
					

		
		
		boolean hasCasePrimitiveKeyWithNull_tMap_6 = false;
		

        // ###############################
        // # Input tables (lookups)
		  boolean rejectedInnerJoin_tMap_6 = false;
		  boolean mainRowRejected_tMap_6 = false;
            				    								  
		// ###############################
        { // start of Var scope
        
	        // ###############################
        	// # Vars tables
        
Var__tMap_6__Struct Var = Var__tMap_6;// ###############################
        // ###############################
        // # Output tables

copyOfout2 = null;


// # Output table : 'copyOfout2'
copyOfout2_tmp.video_tags = row11.video_tags.trim();
copyOfout2_tmp.nb_occ = null;
copyOfout2 = copyOfout2_tmp;
// ###############################

} // end of Var scope

rejectedInnerJoin_tMap_6 = false;










 


	tos_count_tMap_6++;

/**
 * [tMap_6 main ] stop
 */
	
	/**
	 * [tMap_6 process_data_begin ] start
	 */

	

	
	
	currentComponent="tMap_6";

	

 



/**
 * [tMap_6 process_data_begin ] stop
 */
// Start of branch "copyOfout2"
if(copyOfout2 != null) { 



	
	/**
	 * [tAggregateRow_1_AGGOUT main ] start
	 */

	

	
	
		currentVirtualComponent = "tAggregateRow_1";
	
	currentComponent="tAggregateRow_1_AGGOUT";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"copyOfout2"
						
						);
					}
					
	
operation_finder_tAggregateRow_1.video_tags = copyOfout2.video_tags;
			

	operation_finder_tAggregateRow_1.hashCodeDirty = true;
	
	operation_result_tAggregateRow_1 = hash_tAggregateRow_1.get(operation_finder_tAggregateRow_1);

	

	if(operation_result_tAggregateRow_1 == null) { // G_OutMain_AggR_001

		operation_result_tAggregateRow_1 = new AggOperationStruct_tAggregateRow_1();

		operation_result_tAggregateRow_1.video_tags = operation_finder_tAggregateRow_1.video_tags;
				
		
		

		hash_tAggregateRow_1.put(operation_result_tAggregateRow_1, operation_result_tAggregateRow_1);
	
	} // G_OutMain_AggR_001


	
				operation_result_tAggregateRow_1.nb_occ_clmCount++;
				operation_result_tAggregateRow_1.count++;
				


 


	tos_count_tAggregateRow_1_AGGOUT++;

/**
 * [tAggregateRow_1_AGGOUT main ] stop
 */
	
	/**
	 * [tAggregateRow_1_AGGOUT process_data_begin ] start
	 */

	

	
	
		currentVirtualComponent = "tAggregateRow_1";
	
	currentComponent="tAggregateRow_1_AGGOUT";

	

 



/**
 * [tAggregateRow_1_AGGOUT process_data_begin ] stop
 */
	
	/**
	 * [tAggregateRow_1_AGGOUT process_data_end ] start
	 */

	

	
	
		currentVirtualComponent = "tAggregateRow_1";
	
	currentComponent="tAggregateRow_1_AGGOUT";

	

 



/**
 * [tAggregateRow_1_AGGOUT process_data_end ] stop
 */

} // End of branch "copyOfout2"




	
	/**
	 * [tMap_6 process_data_end ] start
	 */

	

	
	
	currentComponent="tMap_6";

	

 



/**
 * [tMap_6 process_data_end ] stop
 */
		// end for
	}



	
	/**
	 * [tNormalize_2 process_data_end ] start
	 */

	

	
	
	currentComponent="tNormalize_2";

	

 



/**
 * [tNormalize_2 process_data_end ] stop
 */



	
	/**
	 * [tMongoDBInput_3 process_data_end ] start
	 */

	

	
	
	currentComponent="tMongoDBInput_3";

	

 



/**
 * [tMongoDBInput_3 process_data_end ] stop
 */
	
	/**
	 * [tMongoDBInput_3 end ] start
	 */

	

	
	
	currentComponent="tMongoDBInput_3";

	

            }
    globalMap.put("tMongoDBInput_3_NB_LINE", nb_line_tMongoDBInput_3);
 

ok_Hash.put("tMongoDBInput_3", true);
end_Hash.put("tMongoDBInput_3", System.currentTimeMillis());




/**
 * [tMongoDBInput_3 end ] stop
 */

	
	/**
	 * [tNormalize_2 end ] start
	 */

	

	
	
	currentComponent="tNormalize_2";

	

    globalMap.put("tNormalize_2_NB_LINE", nb_line_tNormalize_2);
				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row10");
			  	}
			  	
 

ok_Hash.put("tNormalize_2", true);
end_Hash.put("tNormalize_2", System.currentTimeMillis());




/**
 * [tNormalize_2 end ] stop
 */

	
	/**
	 * [tMap_6 end ] start
	 */

	

	
	
	currentComponent="tMap_6";

	


// ###############################
// # Lookup hashes releasing
// ###############################      





				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row11");
			  	}
			  	
 

ok_Hash.put("tMap_6", true);
end_Hash.put("tMap_6", System.currentTimeMillis());




/**
 * [tMap_6 end ] stop
 */

	
	/**
	 * [tAggregateRow_1_AGGOUT end ] start
	 */

	

	
	
		currentVirtualComponent = "tAggregateRow_1";
	
	currentComponent="tAggregateRow_1_AGGOUT";

	

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"copyOfout2");
			  	}
			  	
 

ok_Hash.put("tAggregateRow_1_AGGOUT", true);
end_Hash.put("tAggregateRow_1_AGGOUT", System.currentTimeMillis());




/**
 * [tAggregateRow_1_AGGOUT end ] stop
 */


	
	/**
	 * [tSortRow_2_SortOut begin ] start
	 */

	

	
		
		ok_Hash.put("tSortRow_2_SortOut", false);
		start_Hash.put("tSortRow_2_SortOut", System.currentTimeMillis());
		
	
		currentVirtualComponent = "tSortRow_2";
	
	currentComponent="tSortRow_2_SortOut";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row6");
					}
				
		int tos_count_tSortRow_2_SortOut = 0;
		


class Comparablerow6Struct extends row6Struct implements Comparable<Comparablerow6Struct> {
	
	public int compareTo(Comparablerow6Struct other) {

		if(this.nb_occ == null && other.nb_occ != null){
			return 1;
						
		}else if(this.nb_occ != null && other.nb_occ == null){
			return -1;
						
		}else if(this.nb_occ != null && other.nb_occ != null){
			if(!this.nb_occ.equals(other.nb_occ)){
				return other.nb_occ.compareTo(this.nb_occ);
			}
		}
		return 0;
	}
}

java.util.List<Comparablerow6Struct> list_tSortRow_2_SortOut = new java.util.ArrayList<Comparablerow6Struct>();


 



/**
 * [tSortRow_2_SortOut begin ] stop
 */



	
	/**
	 * [tAggregateRow_1_AGGIN begin ] start
	 */

	

	
		
		ok_Hash.put("tAggregateRow_1_AGGIN", false);
		start_Hash.put("tAggregateRow_1_AGGIN", System.currentTimeMillis());
		
	
		currentVirtualComponent = "tAggregateRow_1";
	
	currentComponent="tAggregateRow_1_AGGIN";

	
		int tos_count_tAggregateRow_1_AGGIN = 0;
		

java.util.Collection<AggOperationStruct_tAggregateRow_1> values_tAggregateRow_1 = hash_tAggregateRow_1.values();

globalMap.put("tAggregateRow_1_NB_LINE", values_tAggregateRow_1.size());

for(AggOperationStruct_tAggregateRow_1 aggregated_row_tAggregateRow_1 : values_tAggregateRow_1) { // G_AggR_600



 



/**
 * [tAggregateRow_1_AGGIN begin ] stop
 */
	
	/**
	 * [tAggregateRow_1_AGGIN main ] start
	 */

	

	
	
		currentVirtualComponent = "tAggregateRow_1";
	
	currentComponent="tAggregateRow_1_AGGIN";

	

            				    row6.tags = aggregated_row_tAggregateRow_1.video_tags;
            				    row6.nb_occ = (int) aggregated_row_tAggregateRow_1.count;
	                                	row6.nb_occ = (int) aggregated_row_tAggregateRow_1.nb_occ_clmCount;
	                                	

 


	tos_count_tAggregateRow_1_AGGIN++;

/**
 * [tAggregateRow_1_AGGIN main ] stop
 */
	
	/**
	 * [tAggregateRow_1_AGGIN process_data_begin ] start
	 */

	

	
	
		currentVirtualComponent = "tAggregateRow_1";
	
	currentComponent="tAggregateRow_1_AGGIN";

	

 



/**
 * [tAggregateRow_1_AGGIN process_data_begin ] stop
 */

	
	/**
	 * [tSortRow_2_SortOut main ] start
	 */

	

	
	
		currentVirtualComponent = "tSortRow_2";
	
	currentComponent="tSortRow_2_SortOut";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row6"
						
						);
					}
					



	Comparablerow6Struct arrayRowtSortRow_2_SortOut = new Comparablerow6Struct();

	arrayRowtSortRow_2_SortOut.tags = row6.tags;
	arrayRowtSortRow_2_SortOut.nb_occ = row6.nb_occ;	
	list_tSortRow_2_SortOut.add(arrayRowtSortRow_2_SortOut);

 


	tos_count_tSortRow_2_SortOut++;

/**
 * [tSortRow_2_SortOut main ] stop
 */
	
	/**
	 * [tSortRow_2_SortOut process_data_begin ] start
	 */

	

	
	
		currentVirtualComponent = "tSortRow_2";
	
	currentComponent="tSortRow_2_SortOut";

	

 



/**
 * [tSortRow_2_SortOut process_data_begin ] stop
 */
	
	/**
	 * [tSortRow_2_SortOut process_data_end ] start
	 */

	

	
	
		currentVirtualComponent = "tSortRow_2";
	
	currentComponent="tSortRow_2_SortOut";

	

 



/**
 * [tSortRow_2_SortOut process_data_end ] stop
 */



	
	/**
	 * [tAggregateRow_1_AGGIN process_data_end ] start
	 */

	

	
	
		currentVirtualComponent = "tAggregateRow_1";
	
	currentComponent="tAggregateRow_1_AGGIN";

	

 



/**
 * [tAggregateRow_1_AGGIN process_data_end ] stop
 */
	
	/**
	 * [tAggregateRow_1_AGGIN end ] start
	 */

	

	
	
		currentVirtualComponent = "tAggregateRow_1";
	
	currentComponent="tAggregateRow_1_AGGIN";

	

} // G_AggR_600

 

ok_Hash.put("tAggregateRow_1_AGGIN", true);
end_Hash.put("tAggregateRow_1_AGGIN", System.currentTimeMillis());




/**
 * [tAggregateRow_1_AGGIN end ] stop
 */

	
	/**
	 * [tSortRow_2_SortOut end ] start
	 */

	

	
	
		currentVirtualComponent = "tSortRow_2";
	
	currentComponent="tSortRow_2_SortOut";

	

row6Struct[] array_tSortRow_2_SortOut = list_tSortRow_2_SortOut.toArray(new Comparablerow6Struct[0]);

java.util.Arrays.sort(array_tSortRow_2_SortOut);

globalMap.put("tSortRow_2",array_tSortRow_2_SortOut);


				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row6");
			  	}
			  	
 

ok_Hash.put("tSortRow_2_SortOut", true);
end_Hash.put("tSortRow_2_SortOut", System.currentTimeMillis());




/**
 * [tSortRow_2_SortOut end ] stop
 */







	
	/**
	 * [tAdvancedHash_row12 begin ] start
	 */

	

	
		
		ok_Hash.put("tAdvancedHash_row12", false);
		start_Hash.put("tAdvancedHash_row12", System.currentTimeMillis());
		
	
	currentComponent="tAdvancedHash_row12";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row12");
					}
				
		int tos_count_tAdvancedHash_row12 = 0;
		

			   		// connection name:row12
			   		// source node:tLogRow_3 - inputs:(out5) outputs:(row12,row12) | target node:tAdvancedHash_row12 - inputs:(row12) outputs:()
			   		// linked node: tMap_5 - inputs:(out1,row12) outputs:(out6)
			   
			   		org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row12 = 
			   			org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;
			   			
			   
	   			org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row12Struct> tHash_Lookup_row12 =org.talend.designer.components.lookup.memory.AdvancedMemoryLookup.
	   						<row12Struct>getLookup(matchingModeEnum_row12);
	   						   
		   	   	   globalMap.put("tHash_Lookup_row12", tHash_Lookup_row12);
		   	   	   
				
           

 



/**
 * [tAdvancedHash_row12 begin ] stop
 */



	
	/**
	 * [tLogRow_3 begin ] start
	 */

	

	
		
		ok_Hash.put("tLogRow_3", false);
		start_Hash.put("tLogRow_3", System.currentTimeMillis());
		
	
	currentComponent="tLogRow_3";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"out5");
					}
				
		int tos_count_tLogRow_3 = 0;
		

	///////////////////////
	
         class Util_tLogRow_3 {

        String[] des_top = { ".", ".", "-", "+" };

        String[] des_head = { "|=", "=|", "-", "+" };

        String[] des_bottom = { "'", "'", "-", "+" };

        String name="";

        java.util.List<String[]> list = new java.util.ArrayList<String[]>();

        int[] colLengths = new int[2];

        public void addRow(String[] row) {

            for (int i = 0; i < 2; i++) {
                if (row[i]!=null) {
                  colLengths[i] = Math.max(colLengths[i], row[i].length());
                }
            }
            list.add(row);
        }

        public void setTableName(String name) {

            this.name = name;
        }

            public StringBuilder format() {
            
                StringBuilder sb = new StringBuilder();
  
            
                    sb.append(print(des_top));
    
                    int totals = 0;
                    for (int i = 0; i < colLengths.length; i++) {
                        totals = totals + colLengths[i];
                    }
    
                    // name
                    sb.append("|");
                    int k = 0;
                    for (k = 0; k < (totals + 1 - name.length()) / 2; k++) {
                        sb.append(' ');
                    }
                    sb.append(name);
                    for (int i = 0; i < totals + 1 - name.length() - k; i++) {
                        sb.append(' ');
                    }
                    sb.append("|\n");

                    // head and rows
                    sb.append(print(des_head));
                    for (int i = 0; i < list.size(); i++) {
    
                        String[] row = list.get(i);
    
                        java.util.Formatter formatter = new java.util.Formatter(new StringBuilder());
                        
                        StringBuilder sbformat = new StringBuilder();                                             
        			        sbformat.append("|%1$-");
        			        sbformat.append(colLengths[0]);
        			        sbformat.append("s");
        			              
        			        sbformat.append("|%2$-");
        			        sbformat.append(colLengths[1]);
        			        sbformat.append("s");
        			                      
                        sbformat.append("|\n");                    
       
                        formatter.format(sbformat.toString(), (Object[])row);	
                                
                        sb.append(formatter.toString());
                        if (i == 0)
                            sb.append(print(des_head)); // print the head
                    }
    
                    // end
                    sb.append(print(des_bottom));
                    return sb;
                }
            

            private StringBuilder print(String[] fillChars) {
                StringBuilder sb = new StringBuilder();
                //first column
                sb.append(fillChars[0]);                
                    for (int i = 0; i < colLengths[0] - fillChars[0].length() + 1; i++) {
                        sb.append(fillChars[2]);
                    }
                    sb.append(fillChars[3]);	                

                
                    //last column
                    for (int i = 0; i < colLengths[1] - fillChars[1].length() + 1; i++) {
                        sb.append(fillChars[2]);
                    }         
                sb.append(fillChars[1]);
                sb.append("\n");               
                return sb;
            }
            
            public boolean isTableEmpty(){
            	if (list.size() > 1)
            		return false;
            	return true;
            }
        }
        Util_tLogRow_3 util_tLogRow_3 = new Util_tLogRow_3();
        util_tLogRow_3.setTableName("tLogRow_3");
        util_tLogRow_3.addRow(new String[]{"tags","name",});        
 		StringBuilder strBuffer_tLogRow_3 = null;
		int nb_line_tLogRow_3 = 0;
///////////////////////    			



 



/**
 * [tLogRow_3 begin ] stop
 */



	
	/**
	 * [tMap_2 begin ] start
	 */

	

	
		
		ok_Hash.put("tMap_2", false);
		start_Hash.put("tMap_2", System.currentTimeMillis());
		
	
	currentComponent="tMap_2";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row8");
					}
				
		int tos_count_tMap_2 = 0;
		




// ###############################
// # Lookup's keys initialization
	
		org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row9Struct> tHash_Lookup_row9 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row9Struct>) 
				((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row9Struct>) 
					globalMap.get( "tHash_Lookup_row9" ))
					;					
					
	

row9Struct row9HashKey = new row9Struct();
row9Struct row9Default = new row9Struct();
// ###############################        

// ###############################
// # Vars initialization
class  Var__tMap_2__Struct  {
}
Var__tMap_2__Struct Var__tMap_2 = new Var__tMap_2__Struct();
// ###############################

// ###############################
// # Outputs initialization
out5Struct out5_tmp = new out5Struct();
// ###############################

        
        



        









 



/**
 * [tMap_2 begin ] stop
 */



	
	/**
	 * [tSampleRow_1 begin ] start
	 */

	

	
		
		ok_Hash.put("tSampleRow_1", false);
		start_Hash.put("tSampleRow_1", System.currentTimeMillis());
		
	
	currentComponent="tSampleRow_1";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row3");
					}
				
		int tos_count_tSampleRow_1 = 0;
		

String[] rangetSampleRow_1 = "1..31".split(",");
java.util.Set rangeSettSampleRow_1 = new java.util.HashSet();

Integer nb_line_tSampleRow_1 = 0;

for(int i =0;i<rangetSampleRow_1.length;i++){

	if(rangetSampleRow_1[i].matches("\\d+")){
	
		rangeSettSampleRow_1 .add(Integer.valueOf(rangetSampleRow_1[i]));
		
	}else if(rangetSampleRow_1[i].matches("\\d+\\.\\.\\d+")){
		
		String[] edgetSampleRow_1= rangetSampleRow_1[i].split("\\.\\.");
		
		for(int j=Integer.valueOf(edgetSampleRow_1[0]).intValue();j<Integer.valueOf(edgetSampleRow_1[1]).intValue()+1;j++){			
			rangeSettSampleRow_1 .add(Integer.valueOf(j));			
		}		
	}else{
	
	}
	
}



 



/**
 * [tSampleRow_1 begin ] stop
 */



	
	/**
	 * [tLogRow_2 begin ] start
	 */

	

	
		
		ok_Hash.put("tLogRow_2", false);
		start_Hash.put("tLogRow_2", System.currentTimeMillis());
		
	
	currentComponent="tLogRow_2";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"out4");
					}
				
		int tos_count_tLogRow_2 = 0;
		

	///////////////////////
	
		final String OUTPUT_FIELD_SEPARATOR_tLogRow_2 = "|";
		java.io.PrintStream consoleOut_tLogRow_2 = null;	

 		StringBuilder strBuffer_tLogRow_2 = null;
		int nb_line_tLogRow_2 = 0;
///////////////////////    			



 



/**
 * [tLogRow_2 begin ] stop
 */



	
	/**
	 * [tMap_4 begin ] start
	 */

	

	
		
		ok_Hash.put("tMap_4", false);
		start_Hash.put("tMap_4", System.currentTimeMillis());
		
	
	currentComponent="tMap_4";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row7");
					}
				
		int tos_count_tMap_4 = 0;
		




// ###############################
// # Lookup's keys initialization
// ###############################        

// ###############################
// # Vars initialization
class  Var__tMap_4__Struct  {
	Integer rank;
}
Var__tMap_4__Struct Var__tMap_4 = new Var__tMap_4__Struct();
// ###############################

// ###############################
// # Outputs initialization
out4Struct out4_tmp = new out4Struct();
// ###############################

        
        



        









 



/**
 * [tMap_4 begin ] stop
 */



	
	/**
	 * [tSortRow_2_SortIn begin ] start
	 */

	

	
		
		ok_Hash.put("tSortRow_2_SortIn", false);
		start_Hash.put("tSortRow_2_SortIn", System.currentTimeMillis());
		
	
		currentVirtualComponent = "tSortRow_2";
	
	currentComponent="tSortRow_2_SortIn";

	
		int tos_count_tSortRow_2_SortIn = 0;
		


row6Struct[] array_tSortRow_2_SortIn = (row6Struct[]) globalMap.remove("tSortRow_2");

int nb_line_tSortRow_2_SortIn = 0;

row6Struct current_tSortRow_2_SortIn = null;

for(int i_tSortRow_2_SortIn = 0; i_tSortRow_2_SortIn < array_tSortRow_2_SortIn.length; i_tSortRow_2_SortIn++){
	current_tSortRow_2_SortIn = array_tSortRow_2_SortIn[i_tSortRow_2_SortIn];
	row7.tags = current_tSortRow_2_SortIn.tags;
	row7.nb_occ = current_tSortRow_2_SortIn.nb_occ;
	// increase number of line sorted
	nb_line_tSortRow_2_SortIn++;

 



/**
 * [tSortRow_2_SortIn begin ] stop
 */
	
	/**
	 * [tSortRow_2_SortIn main ] start
	 */

	

	
	
		currentVirtualComponent = "tSortRow_2";
	
	currentComponent="tSortRow_2_SortIn";

	

 


	tos_count_tSortRow_2_SortIn++;

/**
 * [tSortRow_2_SortIn main ] stop
 */
	
	/**
	 * [tSortRow_2_SortIn process_data_begin ] start
	 */

	

	
	
		currentVirtualComponent = "tSortRow_2";
	
	currentComponent="tSortRow_2_SortIn";

	

 



/**
 * [tSortRow_2_SortIn process_data_begin ] stop
 */

	
	/**
	 * [tMap_4 main ] start
	 */

	

	
	
	currentComponent="tMap_4";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row7"
						
						);
					}
					

		
		
		boolean hasCasePrimitiveKeyWithNull_tMap_4 = false;
		

        // ###############################
        // # Input tables (lookups)
		  boolean rejectedInnerJoin_tMap_4 = false;
		  boolean mainRowRejected_tMap_4 = false;
            				    								  
		// ###############################
        { // start of Var scope
        
	        // ###############################
        	// # Vars tables
        
Var__tMap_4__Struct Var = Var__tMap_4;
Var.rank = Numeric.sequence("rankSeq",1,1);// ###############################
        // ###############################
        // # Output tables

out4 = null;


// # Output table : 'out4'
out4_tmp.tags = row7.tags ;
out4_tmp.nb_occ = row7.nb_occ ;
out4_tmp.rank = Var.rank ;
out4 = out4_tmp;
// ###############################

} // end of Var scope

rejectedInnerJoin_tMap_4 = false;










 


	tos_count_tMap_4++;

/**
 * [tMap_4 main ] stop
 */
	
	/**
	 * [tMap_4 process_data_begin ] start
	 */

	

	
	
	currentComponent="tMap_4";

	

 



/**
 * [tMap_4 process_data_begin ] stop
 */
// Start of branch "out4"
if(out4 != null) { 



	
	/**
	 * [tLogRow_2 main ] start
	 */

	

	
	
	currentComponent="tLogRow_2";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"out4"
						
						);
					}
					
///////////////////////		
						



				strBuffer_tLogRow_2 = new StringBuilder();




   				
	    		if(out4.tags != null) { //              
                    							
       
				strBuffer_tLogRow_2.append(
				                String.valueOf(out4.tags)							
				);


							
	    		} //  			

    			strBuffer_tLogRow_2.append("|");
    			


   				
	    		if(out4.nb_occ != null) { //              
                    							
       
				strBuffer_tLogRow_2.append(
				                String.valueOf(out4.nb_occ)							
				);


							
	    		} //  			

    			strBuffer_tLogRow_2.append("|");
    			


   				
	    		if(out4.rank != null) { //              
                    							
       
				strBuffer_tLogRow_2.append(
				                String.valueOf(out4.rank)							
				);


							
	    		} //  			
 

                    if (globalMap.get("tLogRow_CONSOLE")!=null)
                    {
                    	consoleOut_tLogRow_2 = (java.io.PrintStream) globalMap.get("tLogRow_CONSOLE");
                    }
                    else
                    {
                    	consoleOut_tLogRow_2 = new java.io.PrintStream(new java.io.BufferedOutputStream(System.out));
                    	globalMap.put("tLogRow_CONSOLE",consoleOut_tLogRow_2);
                    }
                    consoleOut_tLogRow_2.println(strBuffer_tLogRow_2.toString());
                    consoleOut_tLogRow_2.flush();
                    nb_line_tLogRow_2++;
//////

//////                    
                    
///////////////////////    			

 
     row3 = out4;


	tos_count_tLogRow_2++;

/**
 * [tLogRow_2 main ] stop
 */
	
	/**
	 * [tLogRow_2 process_data_begin ] start
	 */

	

	
	
	currentComponent="tLogRow_2";

	

 



/**
 * [tLogRow_2 process_data_begin ] stop
 */

	
	/**
	 * [tSampleRow_1 main ] start
	 */

	

	
	
	currentComponent="tSampleRow_1";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row3"
						
						);
					}
					

	
	nb_line_tSampleRow_1++;
	
 	if(!rangeSettSampleRow_1.contains(nb_line_tSampleRow_1)){        
            row8 = null;            	
 	}else{  	
 	   row8 = new row8Struct();
 	   

       row8.tags = row3.tags;
       

       row8.nb_occ = row3.nb_occ;
       

       row8.rank = row3.rank;
       
 	
 	}
 
 
 
 
 
 
 
 
 
 
 
 
 


	tos_count_tSampleRow_1++;

/**
 * [tSampleRow_1 main ] stop
 */
	
	/**
	 * [tSampleRow_1 process_data_begin ] start
	 */

	

	
	
	currentComponent="tSampleRow_1";

	

 



/**
 * [tSampleRow_1 process_data_begin ] stop
 */
// Start of branch "row8"
if(row8 != null) { 



	
	/**
	 * [tMap_2 main ] start
	 */

	

	
	
	currentComponent="tMap_2";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row8"
						
						);
					}
					

		
		
		boolean hasCasePrimitiveKeyWithNull_tMap_2 = false;
		

        // ###############################
        // # Input tables (lookups)
		  boolean rejectedInnerJoin_tMap_2 = false;
		  boolean mainRowRejected_tMap_2 = false;
            				    								  
		

				///////////////////////////////////////////////
				// Starting Lookup Table "row9" 
				///////////////////////////////////////////////


				
				
                            
 					    boolean forceLooprow9 = false;
       		  	    	
       		  	    	
 							row9Struct row9ObjectFromLookup = null;
                          
		           		  	if(!rejectedInnerJoin_tMap_2) { // G_TM_M_020

								
								hasCasePrimitiveKeyWithNull_tMap_2 = false;
								
                        		    		    row9HashKey.rank = row8.rank ;
                        		    		

								
		                        	row9HashKey.hashCodeDirty = true;
                        		
	  					
	  							
			  					
			  					
	  					
		  							tHash_Lookup_row9.lookup( row9HashKey );

	  							

	  							

 								
		  				
	  								
						
									
  									  		
 								



							} // G_TM_M_020
			           		  	  
							
				           		if(tHash_Lookup_row9 != null && tHash_Lookup_row9.getCount(row9HashKey) > 1) { // G 071
			  							
			  						
									 		
									//System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row9' and it contains more one result from keys :  row9.rank = '" + row9HashKey.rank + "'");
								} // G 071
							

							row9Struct row9 = null;
                    		  	 
							   
                    		  	 
	       		  	    	row9Struct fromLookup_row9 = null;
							row9 = row9Default;
										 
							
								 
							
							
								if (tHash_Lookup_row9 !=null && tHash_Lookup_row9.hasNext()) { // G 099
								
							
								
								fromLookup_row9 = tHash_Lookup_row9.next();

							
							
								} // G 099
							
							

							if(fromLookup_row9 != null) {
								row9 = fromLookup_row9;
							}
							
							
							
			  							
								
	                    		  	
		                    
	            	
	            	
	            // ###############################
        { // start of Var scope
        
	        // ###############################
        	// # Vars tables
        
Var__tMap_2__Struct Var = Var__tMap_2;// ###############################
        // ###############################
        // # Output tables

out5 = null;


// # Output table : 'out5'
out5_tmp.tags = row8.tags ;
out5_tmp.name = row9.name ;
out5 = out5_tmp;
// ###############################

} // end of Var scope

rejectedInnerJoin_tMap_2 = false;










 


	tos_count_tMap_2++;

/**
 * [tMap_2 main ] stop
 */
	
	/**
	 * [tMap_2 process_data_begin ] start
	 */

	

	
	
	currentComponent="tMap_2";

	

 



/**
 * [tMap_2 process_data_begin ] stop
 */
// Start of branch "out5"
if(out5 != null) { 



	
	/**
	 * [tLogRow_3 main ] start
	 */

	

	
	
	currentComponent="tLogRow_3";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"out5"
						
						);
					}
					
///////////////////////		
						

				
				String[] row_tLogRow_3 = new String[2];
   				
	    		if(out5.tags != null) { //              
                 row_tLogRow_3[0]=    						    
				                String.valueOf(out5.tags)			
					          ;	
							
	    		} //			
    			   				
	    		if(out5.name != null) { //              
                 row_tLogRow_3[1]=    						    
				                String.valueOf(out5.name)			
					          ;	
							
	    		} //			
    			 

				util_tLogRow_3.addRow(row_tLogRow_3);	
				nb_line_tLogRow_3++;
//////

//////                    
                    
///////////////////////    			

 
     row12 = out5;


	tos_count_tLogRow_3++;

/**
 * [tLogRow_3 main ] stop
 */
	
	/**
	 * [tLogRow_3 process_data_begin ] start
	 */

	

	
	
	currentComponent="tLogRow_3";

	

 



/**
 * [tLogRow_3 process_data_begin ] stop
 */

	
	/**
	 * [tAdvancedHash_row12 main ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row12";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row12"
						
						);
					}
					


			   
			   

					row12Struct row12_HashRow = new row12Struct();
		   	   	   
				
				row12_HashRow.tags = row12.tags;
				
				row12_HashRow.name = row12.name;
				
			tHash_Lookup_row12.put(row12_HashRow);
			
            




 


	tos_count_tAdvancedHash_row12++;

/**
 * [tAdvancedHash_row12 main ] stop
 */
	
	/**
	 * [tAdvancedHash_row12 process_data_begin ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row12";

	

 



/**
 * [tAdvancedHash_row12 process_data_begin ] stop
 */
	
	/**
	 * [tAdvancedHash_row12 process_data_end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row12";

	

 



/**
 * [tAdvancedHash_row12 process_data_end ] stop
 */



	
	/**
	 * [tLogRow_3 process_data_end ] start
	 */

	

	
	
	currentComponent="tLogRow_3";

	

 



/**
 * [tLogRow_3 process_data_end ] stop
 */

} // End of branch "out5"




	
	/**
	 * [tMap_2 process_data_end ] start
	 */

	

	
	
	currentComponent="tMap_2";

	

 



/**
 * [tMap_2 process_data_end ] stop
 */

} // End of branch "row8"




	
	/**
	 * [tSampleRow_1 process_data_end ] start
	 */

	

	
	
	currentComponent="tSampleRow_1";

	

 



/**
 * [tSampleRow_1 process_data_end ] stop
 */



	
	/**
	 * [tLogRow_2 process_data_end ] start
	 */

	

	
	
	currentComponent="tLogRow_2";

	

 



/**
 * [tLogRow_2 process_data_end ] stop
 */

} // End of branch "out4"




	
	/**
	 * [tMap_4 process_data_end ] start
	 */

	

	
	
	currentComponent="tMap_4";

	

 



/**
 * [tMap_4 process_data_end ] stop
 */



	
	/**
	 * [tSortRow_2_SortIn process_data_end ] start
	 */

	

	
	
		currentVirtualComponent = "tSortRow_2";
	
	currentComponent="tSortRow_2_SortIn";

	

 



/**
 * [tSortRow_2_SortIn process_data_end ] stop
 */
	
	/**
	 * [tSortRow_2_SortIn end ] start
	 */

	

	
	
		currentVirtualComponent = "tSortRow_2";
	
	currentComponent="tSortRow_2_SortIn";

	


}

globalMap.put("tSortRow_2_SortIn_NB_LINE",nb_line_tSortRow_2_SortIn);

 

ok_Hash.put("tSortRow_2_SortIn", true);
end_Hash.put("tSortRow_2_SortIn", System.currentTimeMillis());




/**
 * [tSortRow_2_SortIn end ] stop
 */

	
	/**
	 * [tMap_4 end ] start
	 */

	

	
	
	currentComponent="tMap_4";

	


// ###############################
// # Lookup hashes releasing
// ###############################      





				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row7");
			  	}
			  	
 

ok_Hash.put("tMap_4", true);
end_Hash.put("tMap_4", System.currentTimeMillis());




/**
 * [tMap_4 end ] stop
 */

	
	/**
	 * [tLogRow_2 end ] start
	 */

	

	
	
	currentComponent="tLogRow_2";

	


//////
//////
globalMap.put("tLogRow_2_NB_LINE",nb_line_tLogRow_2);

///////////////////////    			

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"out4");
			  	}
			  	
 

ok_Hash.put("tLogRow_2", true);
end_Hash.put("tLogRow_2", System.currentTimeMillis());




/**
 * [tLogRow_2 end ] stop
 */

	
	/**
	 * [tSampleRow_1 end ] start
	 */

	

	
	
	currentComponent="tSampleRow_1";

	

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row3");
			  	}
			  	
 

ok_Hash.put("tSampleRow_1", true);
end_Hash.put("tSampleRow_1", System.currentTimeMillis());




/**
 * [tSampleRow_1 end ] stop
 */

	
	/**
	 * [tMap_2 end ] start
	 */

	

	
	
	currentComponent="tMap_2";

	


// ###############################
// # Lookup hashes releasing
					if(tHash_Lookup_row9 != null) {
						tHash_Lookup_row9.endGet();
					}
					globalMap.remove( "tHash_Lookup_row9" );

					
					
				
// ###############################      





				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row8");
			  	}
			  	
 

ok_Hash.put("tMap_2", true);
end_Hash.put("tMap_2", System.currentTimeMillis());




/**
 * [tMap_2 end ] stop
 */

	
	/**
	 * [tLogRow_3 end ] start
	 */

	

	
	
	currentComponent="tLogRow_3";

	


//////

                    
                    java.io.PrintStream consoleOut_tLogRow_3 = null;
                    if (globalMap.get("tLogRow_CONSOLE")!=null)
                    {
                    	consoleOut_tLogRow_3 = (java.io.PrintStream) globalMap.get("tLogRow_CONSOLE");
                    }
                    else
                    {
                    	consoleOut_tLogRow_3 = new java.io.PrintStream(new java.io.BufferedOutputStream(System.out));
                    	globalMap.put("tLogRow_CONSOLE",consoleOut_tLogRow_3);
                    }
                    
                    consoleOut_tLogRow_3.println(util_tLogRow_3.format().toString());
                    consoleOut_tLogRow_3.flush();
//////
globalMap.put("tLogRow_3_NB_LINE",nb_line_tLogRow_3);

///////////////////////    			

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"out5");
			  	}
			  	
 

ok_Hash.put("tLogRow_3", true);
end_Hash.put("tLogRow_3", System.currentTimeMillis());




/**
 * [tLogRow_3 end ] stop
 */

	
	/**
	 * [tAdvancedHash_row12 end ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row12";

	

tHash_Lookup_row12.endPut();

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row12");
			  	}
			  	
 

ok_Hash.put("tAdvancedHash_row12", true);
end_Hash.put("tAdvancedHash_row12", System.currentTimeMillis());




/**
 * [tAdvancedHash_row12 end ] stop
 */




































				}//end the resume

				



	
			}catch(java.lang.Exception e){	
				
				TalendException te = new TalendException(e, currentComponent, globalMap);
				
					te.setVirtualComponentName(currentVirtualComponent);
				
				throw te;
			}catch(java.lang.Error error){	
				
					runStat.stopThreadStat();
				
				throw error;
			}finally{
				
					     			//free memory for "tMap_2"
					     			globalMap.remove("tHash_Lookup_row9"); 
				     			
							//free memory for "tSortRow_2_SortIn"
							globalMap.remove("tSortRow_2");
						
							//free memory for "tAggregateRow_1_AGGIN"
							globalMap.remove("tAggregateRow_1");
						
				try{
					
	
	/**
	 * [tMongoDBInput_3 finally ] start
	 */

	

	
	
	currentComponent="tMongoDBInput_3";

	

 



/**
 * [tMongoDBInput_3 finally ] stop
 */

	
	/**
	 * [tNormalize_2 finally ] start
	 */

	

	
	
	currentComponent="tNormalize_2";

	

 



/**
 * [tNormalize_2 finally ] stop
 */

	
	/**
	 * [tMap_6 finally ] start
	 */

	

	
	
	currentComponent="tMap_6";

	

 



/**
 * [tMap_6 finally ] stop
 */

	
	/**
	 * [tAggregateRow_1_AGGOUT finally ] start
	 */

	

	
	
		currentVirtualComponent = "tAggregateRow_1";
	
	currentComponent="tAggregateRow_1_AGGOUT";

	

 



/**
 * [tAggregateRow_1_AGGOUT finally ] stop
 */

	
	/**
	 * [tAggregateRow_1_AGGIN finally ] start
	 */

	

	
	
		currentVirtualComponent = "tAggregateRow_1";
	
	currentComponent="tAggregateRow_1_AGGIN";

	

 



/**
 * [tAggregateRow_1_AGGIN finally ] stop
 */

	
	/**
	 * [tSortRow_2_SortOut finally ] start
	 */

	

	
	
		currentVirtualComponent = "tSortRow_2";
	
	currentComponent="tSortRow_2_SortOut";

	

 



/**
 * [tSortRow_2_SortOut finally ] stop
 */

	
	/**
	 * [tSortRow_2_SortIn finally ] start
	 */

	

	
	
		currentVirtualComponent = "tSortRow_2";
	
	currentComponent="tSortRow_2_SortIn";

	

 



/**
 * [tSortRow_2_SortIn finally ] stop
 */

	
	/**
	 * [tMap_4 finally ] start
	 */

	

	
	
	currentComponent="tMap_4";

	

 



/**
 * [tMap_4 finally ] stop
 */

	
	/**
	 * [tLogRow_2 finally ] start
	 */

	

	
	
	currentComponent="tLogRow_2";

	

 



/**
 * [tLogRow_2 finally ] stop
 */

	
	/**
	 * [tSampleRow_1 finally ] start
	 */

	

	
	
	currentComponent="tSampleRow_1";

	

 



/**
 * [tSampleRow_1 finally ] stop
 */

	
	/**
	 * [tMap_2 finally ] start
	 */

	

	
	
	currentComponent="tMap_2";

	

 



/**
 * [tMap_2 finally ] stop
 */

	
	/**
	 * [tLogRow_3 finally ] start
	 */

	

	
	
	currentComponent="tLogRow_3";

	

 



/**
 * [tLogRow_3 finally ] stop
 */

	
	/**
	 * [tAdvancedHash_row12 finally ] start
	 */

	

	
	
	currentComponent="tAdvancedHash_row12";

	

 



/**
 * [tAdvancedHash_row12 finally ] stop
 */




































				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tMongoDBInput_3_SUBPROCESS_STATE", 1);
	}
	

public void tDBConnection_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
	globalMap.put("tDBConnection_1_SUBPROCESS_STATE", 0);

 final boolean execStat = this.execStat;
	
		String iterateId = "";
	
	
	String currentComponent = "";
	java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

	try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { //start the resume
				globalResumeTicket = true;





	
	/**
	 * [tDBConnection_1 begin ] start
	 */

	

	
		
		ok_Hash.put("tDBConnection_1", false);
		start_Hash.put("tDBConnection_1", System.currentTimeMillis());
		
	
	currentComponent="tDBConnection_1";

	
		int tos_count_tDBConnection_1 = 0;
		


	
            String dbProperties_tDBConnection_1 = "";
            String url_tDBConnection_1 = "jdbc:postgresql://"+"localhost"+":"+"5432"+"/"+"ville-t";
            
            if(dbProperties_tDBConnection_1 != null && !"".equals(dbProperties_tDBConnection_1.trim())) {
                url_tDBConnection_1 = url_tDBConnection_1 + "?" + dbProperties_tDBConnection_1;
            }
	String dbUser_tDBConnection_1 = "fimafeng";
	
	
		 
	final String decryptedPassword_tDBConnection_1 = routines.system.PasswordEncryptUtil.decryptPassword("enc:routine.encryption.key.v1:NMYnP4L3HsJjauo+luXnCITePpo7f4YBw2aPHxFN7KNdsou1Z9SAZA==");
		String dbPwd_tDBConnection_1 = decryptedPassword_tDBConnection_1;
	
	
	java.sql.Connection conn_tDBConnection_1 = null;
	
        java.util.Enumeration<java.sql.Driver> drivers_tDBConnection_1 =  java.sql.DriverManager.getDrivers();
        java.util.Set<String> redShiftDriverNames_tDBConnection_1 = new java.util.HashSet<String>(java.util.Arrays
                .asList("com.amazon.redshift.jdbc.Driver","com.amazon.redshift.jdbc41.Driver","com.amazon.redshift.jdbc42.Driver"));
    while (drivers_tDBConnection_1.hasMoreElements()) {
        java.sql.Driver d_tDBConnection_1 = drivers_tDBConnection_1.nextElement();
        if (redShiftDriverNames_tDBConnection_1.contains(d_tDBConnection_1.getClass().getName())) {
            try {
                java.sql.DriverManager.deregisterDriver(d_tDBConnection_1);
                java.sql.DriverManager.registerDriver(d_tDBConnection_1);
            } catch (java.lang.Exception e_tDBConnection_1) {
globalMap.put("tDBConnection_1_ERROR_MESSAGE",e_tDBConnection_1.getMessage());
                    //do nothing
            }
        }
    }
					String driverClass_tDBConnection_1 = "org.postgresql.Driver";
			java.lang.Class jdbcclazz_tDBConnection_1 = java.lang.Class.forName(driverClass_tDBConnection_1);
			globalMap.put("driverClass_tDBConnection_1", driverClass_tDBConnection_1);
		
			conn_tDBConnection_1 = java.sql.DriverManager.getConnection(url_tDBConnection_1,dbUser_tDBConnection_1,dbPwd_tDBConnection_1);

		globalMap.put("conn_tDBConnection_1", conn_tDBConnection_1);
	if (null != conn_tDBConnection_1) {
		
			conn_tDBConnection_1.setAutoCommit(false);
	}

	globalMap.put("schema_" + "tDBConnection_1","");

 



/**
 * [tDBConnection_1 begin ] stop
 */
	
	/**
	 * [tDBConnection_1 main ] start
	 */

	

	
	
	currentComponent="tDBConnection_1";

	

 


	tos_count_tDBConnection_1++;

/**
 * [tDBConnection_1 main ] stop
 */
	
	/**
	 * [tDBConnection_1 process_data_begin ] start
	 */

	

	
	
	currentComponent="tDBConnection_1";

	

 



/**
 * [tDBConnection_1 process_data_begin ] stop
 */
	
	/**
	 * [tDBConnection_1 process_data_end ] start
	 */

	

	
	
	currentComponent="tDBConnection_1";

	

 



/**
 * [tDBConnection_1 process_data_end ] stop
 */
	
	/**
	 * [tDBConnection_1 end ] start
	 */

	

	
	
	currentComponent="tDBConnection_1";

	

 

ok_Hash.put("tDBConnection_1", true);
end_Hash.put("tDBConnection_1", System.currentTimeMillis());




/**
 * [tDBConnection_1 end ] stop
 */
				}//end the resume

				



	
			}catch(java.lang.Exception e){	
				
				TalendException te = new TalendException(e, currentComponent, globalMap);
				
				throw te;
			}catch(java.lang.Error error){	
				
					runStat.stopThreadStat();
				
				throw error;
			}finally{
				
				try{
					
	
	/**
	 * [tDBConnection_1 finally ] start
	 */

	

	
	
	currentComponent="tDBConnection_1";

	

 



/**
 * [tDBConnection_1 finally ] stop
 */
				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
				}
				resourceMap = null;
			}
		

		globalMap.put("tDBConnection_1_SUBPROCESS_STATE", 1);
	}
	
    public String resuming_logs_dir_path = null;
    public String resuming_checkpoint_path = null;
    public String parent_part_launcher = null;
    private String resumeEntryMethodName = null;
    private boolean globalResumeTicket = false;

    public boolean watch = false;
    // portStats is null, it means don't execute the statistics
    public Integer portStats = null;
    public int portTraces = 4334;
    public String clientHost;
    public String defaultClientHost = "localhost";
    public String contextStr = "Default";
    public boolean isDefaultContext = true;
    public String pid = "0";
    public String rootPid = null;
    public String fatherPid = null;
    public String fatherNode = null;
    public long startTime = 0;
    public boolean isChildJob = false;
    public String log4jLevel = "";
    
    private boolean enableLogStash;

    private boolean execStat = true;

    private ThreadLocal<java.util.Map<String, String>> threadLocal = new ThreadLocal<java.util.Map<String, String>>() {
        protected java.util.Map<String, String> initialValue() {
            java.util.Map<String,String> threadRunResultMap = new java.util.HashMap<String, String>();
            threadRunResultMap.put("errorCode", null);
            threadRunResultMap.put("status", "");
            return threadRunResultMap;
        };
    };


    protected PropertiesWithType context_param = new PropertiesWithType();
    public java.util.Map<String, Object> parentContextMap = new java.util.HashMap<String, Object>();

    public String status= "";
    

    public static void main(String[] args){
        final SilverToGold SilverToGoldClass = new SilverToGold();

        int exitCode = SilverToGoldClass.runJobInTOS(args);

        System.exit(exitCode);
    }


    public String[][] runJob(String[] args) {

        int exitCode = runJobInTOS(args);
        String[][] bufferValue = new String[][] { { Integer.toString(exitCode) } };

        return bufferValue;
    }

    public boolean hastBufferOutputComponent() {
		boolean hastBufferOutput = false;
    	
        return hastBufferOutput;
    }

    public int runJobInTOS(String[] args) {
	   	// reset status
	   	status = "";
	   	
        String lastStr = "";
        for (String arg : args) {
            if (arg.equalsIgnoreCase("--context_param")) {
                lastStr = arg;
            } else if (lastStr.equals("")) {
                evalParam(arg);
            } else {
                evalParam(lastStr + " " + arg);
                lastStr = "";
            }
        }
        enableLogStash = "true".equalsIgnoreCase(System.getProperty("audit.enabled"));

    	
    	

        if(clientHost == null) {
            clientHost = defaultClientHost;
        }

        if(pid == null || "0".equals(pid)) {
            pid = TalendString.getAsciiRandomString(6);
        }

        if (rootPid==null) {
            rootPid = pid;
        }
        if (fatherPid==null) {
            fatherPid = pid;
        }else{
            isChildJob = true;
        }

        if (portStats != null) {
            // portStats = -1; //for testing
            if (portStats < 0 || portStats > 65535) {
                // issue:10869, the portStats is invalid, so this client socket can't open
                System.err.println("The statistics socket port " + portStats + " is invalid.");
                execStat = false;
            }
        } else {
            execStat = false;
        }
        boolean inOSGi = routines.system.BundleUtils.inOSGi();

        if (inOSGi) {
            java.util.Dictionary<String, Object> jobProperties = routines.system.BundleUtils.getJobProperties(jobName);

            if (jobProperties != null && jobProperties.get("context") != null) {
                contextStr = (String)jobProperties.get("context");
            }
        }

        try {
            //call job/subjob with an existing context, like: --context=production. if without this parameter, there will use the default context instead.
            java.io.InputStream inContext = SilverToGold.class.getClassLoader().getResourceAsStream("local_project/silvertogold_0_1/contexts/" + contextStr + ".properties");
            if (inContext == null) {
                inContext = SilverToGold.class.getClassLoader().getResourceAsStream("config/contexts/" + contextStr + ".properties");
            }
            if (inContext != null) {
                try {
                    //defaultProps is in order to keep the original context value
                    if(context != null && context.isEmpty()) {
	                defaultProps.load(inContext);
	                context = new ContextProperties(defaultProps);
                    }
                } finally {
                    inContext.close();
                }
            } else if (!isDefaultContext) {
                //print info and job continue to run, for case: context_param is not empty.
                System.err.println("Could not find the context " + contextStr);
            }

            if(!context_param.isEmpty()) {
                context.putAll(context_param);
				//set types for params from parentJobs
				for (Object key: context_param.keySet()){
					String context_key = key.toString();
					String context_type = context_param.getContextType(context_key);
					context.setContextType(context_key, context_type);

				}
            }
            class ContextProcessing {
                private void processContext_0() {
                } 
                public void processAllContext() {
                        processContext_0();
                }
            }

            new ContextProcessing().processAllContext();
        } catch (java.io.IOException ie) {
            System.err.println("Could not load context "+contextStr);
            ie.printStackTrace();
        }

        // get context value from parent directly
        if (parentContextMap != null && !parentContextMap.isEmpty()) {
        }

        //Resume: init the resumeUtil
        resumeEntryMethodName = ResumeUtil.getResumeEntryMethodName(resuming_checkpoint_path);
        resumeUtil = new ResumeUtil(resuming_logs_dir_path, isChildJob, rootPid);
        resumeUtil.initCommonInfo(pid, rootPid, fatherPid, projectName, jobName, contextStr, jobVersion);

		List<String> parametersToEncrypt = new java.util.ArrayList<String>();
        //Resume: jobStart
        resumeUtil.addLog("JOB_STARTED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "", "","","","",resumeUtil.convertToJsonText(context,parametersToEncrypt));

if(execStat) {
    try {
        runStat.openSocket(!isChildJob);
        runStat.setAllPID(rootPid, fatherPid, pid, jobName);
        runStat.startThreadStat(clientHost, portStats);
        runStat.updateStatOnJob(RunStat.JOBSTART, fatherNode);
    } catch (java.io.IOException ioException) {
        ioException.printStackTrace();
    }
}



	
	    java.util.concurrent.ConcurrentHashMap<Object, Object> concurrentHashMap = new java.util.concurrent.ConcurrentHashMap<Object, Object>();
	    globalMap.put("concurrentHashMap", concurrentHashMap);
	

    long startUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    long endUsedMemory = 0;
    long end = 0;

    startTime = System.currentTimeMillis();


this.globalResumeTicket = true;//to run tPreJob





this.globalResumeTicket = false;//to run others jobs

try {
errorCode = null;tMongoDBConnection_1Process(globalMap);
if(!"failure".equals(status)) { status = "end"; }
}catch (TalendException e_tMongoDBConnection_1) {
globalMap.put("tMongoDBConnection_1_SUBPROCESS_STATE", -1);

e_tMongoDBConnection_1.printStackTrace();

}
try {
errorCode = null;tDBConnection_1Process(globalMap);
if(!"failure".equals(status)) { status = "end"; }
}catch (TalendException e_tDBConnection_1) {
globalMap.put("tDBConnection_1_SUBPROCESS_STATE", -1);

e_tDBConnection_1.printStackTrace();

}

this.globalResumeTicket = true;//to run tPostJob




        end = System.currentTimeMillis();

        if (watch) {
            System.out.println((end-startTime)+" milliseconds");
        }

        endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        if (false) {
            System.out.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : SilverToGold");
        }



if (execStat) {
    runStat.updateStatOnJob(RunStat.JOBEND, fatherNode);
    runStat.stopThreadStat();
}
    int returnCode = 0;


    if(errorCode == null) {
         returnCode = status != null && status.equals("failure") ? 1 : 0;
    } else {
         returnCode = errorCode.intValue();
    }
    resumeUtil.addLog("JOB_ENDED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "", "","" + returnCode,"","","");

    return returnCode;

  }

    // only for OSGi env
    public void destroy() {
    closeSqlDbConnections();


    }



    private void closeSqlDbConnections() {
        try {
            Object obj_conn;
            obj_conn = globalMap.remove("conn_tDBConnection_1");
            if (null != obj_conn) {
                ((java.sql.Connection) obj_conn).close();
            }
        } catch (java.lang.Exception e) {
        }
    }











    private java.util.Map<String, Object> getSharedConnections4REST() {
        java.util.Map<String, Object> connections = new java.util.HashMap<String, Object>();
            connections.put("conn_tDBConnection_1", globalMap.get("conn_tDBConnection_1"));






        return connections;
    }

    private void evalParam(String arg) {
        if (arg.startsWith("--resuming_logs_dir_path")) {
            resuming_logs_dir_path = arg.substring(25);
        } else if (arg.startsWith("--resuming_checkpoint_path")) {
            resuming_checkpoint_path = arg.substring(27);
        } else if (arg.startsWith("--parent_part_launcher")) {
            parent_part_launcher = arg.substring(23);
        } else if (arg.startsWith("--watch")) {
            watch = true;
        } else if (arg.startsWith("--stat_port=")) {
            String portStatsStr = arg.substring(12);
            if (portStatsStr != null && !portStatsStr.equals("null")) {
                portStats = Integer.parseInt(portStatsStr);
            }
        } else if (arg.startsWith("--trace_port=")) {
            portTraces = Integer.parseInt(arg.substring(13));
        } else if (arg.startsWith("--client_host=")) {
            clientHost = arg.substring(14);
        } else if (arg.startsWith("--context=")) {
            contextStr = arg.substring(10);
            isDefaultContext = false;
        } else if (arg.startsWith("--father_pid=")) {
            fatherPid = arg.substring(13);
        } else if (arg.startsWith("--root_pid=")) {
            rootPid = arg.substring(11);
        } else if (arg.startsWith("--father_node=")) {
            fatherNode = arg.substring(14);
        } else if (arg.startsWith("--pid=")) {
            pid = arg.substring(6);
        } else if (arg.startsWith("--context_type")) {
            String keyValue = arg.substring(15);
			int index = -1;
            if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
                if (fatherPid==null) {
                    context_param.setContextType(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
                } else { // the subjob won't escape the especial chars
                    context_param.setContextType(keyValue.substring(0, index), keyValue.substring(index + 1) );
                }

            }

		} else if (arg.startsWith("--context_param")) {
            String keyValue = arg.substring(16);
            int index = -1;
            if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
                if (fatherPid==null) {
                    context_param.put(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
                } else { // the subjob won't escape the especial chars
                    context_param.put(keyValue.substring(0, index), keyValue.substring(index + 1) );
                }
            }
        } else if (arg.startsWith("--log4jLevel=")) {
            log4jLevel = arg.substring(13);
		} else if (arg.startsWith("--audit.enabled") && arg.contains("=")) {//for trunjob call
		    final int equal = arg.indexOf('=');
			final String key = arg.substring("--".length(), equal);
			System.setProperty(key, arg.substring(equal + 1));
		}
    }
    
    private static final String NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY = "<TALEND_NULL>";

    private final String[][] escapeChars = {
        {"\\\\","\\"},{"\\n","\n"},{"\\'","\'"},{"\\r","\r"},
        {"\\f","\f"},{"\\b","\b"},{"\\t","\t"}
        };
    private String replaceEscapeChars (String keyValue) {

		if (keyValue == null || ("").equals(keyValue.trim())) {
			return keyValue;
		}

		StringBuilder result = new StringBuilder();
		int currIndex = 0;
		while (currIndex < keyValue.length()) {
			int index = -1;
			// judege if the left string includes escape chars
			for (String[] strArray : escapeChars) {
				index = keyValue.indexOf(strArray[0],currIndex);
				if (index>=0) {

					result.append(keyValue.substring(currIndex, index + strArray[0].length()).replace(strArray[0], strArray[1]));
					currIndex = index + strArray[0].length();
					break;
				}
			}
			// if the left string doesn't include escape chars, append the left into the result
			if (index < 0) {
				result.append(keyValue.substring(currIndex));
				currIndex = currIndex + keyValue.length();
			}
		}

		return result.toString();
    }

    public Integer getErrorCode() {
        return errorCode;
    }


    public String getStatus() {
        return status;
    }

    ResumeUtil resumeUtil = null;
}
/************************************************************************************************
 *     328396 characters generated by Talend Open Studio for Big Data 
 *     on the 27 mars 2026, 16:43:44 CET
 ************************************************************************************************/