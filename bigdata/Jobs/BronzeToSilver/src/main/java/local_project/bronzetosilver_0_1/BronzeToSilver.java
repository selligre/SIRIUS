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


package local_project.bronzetosilver_0_1;

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
 * Job: BronzeToSilver Purpose: <br>
 * Description:  <br>
 * @author user@talend.com
 * @version 8.0.1.20211109_1610
 * @status 
 */
public class BronzeToSilver implements TalendJob {

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
	private final String jobName = "BronzeToSilver";
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
				BronzeToSilver.this.exception = e;
			}
		}
		if (!(e instanceof TalendException)) {
		try {
			for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
				if (m.getName().compareTo(currentComponent + "_error") == 0) {
					m.invoke(BronzeToSilver.this, new Object[] { e , currentComponent, globalMap});
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
			
			public void tMap_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tLogRow_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tMongoDBOutput_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tLogRow_2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tMongoDBOutput_2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tMongoDBInput_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tMongoDBConnection_1_onSubJobError(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {

resumeUtil.addLog("SYSTEM_LOG", "NODE:"+ errorComponent, "", Thread.currentThread().getId()+ "", "FATAL", "", exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception),"");

			}
			public void tMongoDBInput_1_onSubJobError(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {

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
	final String decryptedPassword_tMongoDBConnection_1 = routines.system.PasswordEncryptUtil.decryptPassword("enc:routine.encryption.key.v1:QUxx4iIHnvmm3yWetwLIE92MnSxQSJsSbvXI8E1OzZshziE7PhDe+Q==");
                        
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
	


public static class row3Struct implements routines.system.IPersistableRow<row3Struct> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_BronzeToSilver = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_BronzeToSilver = new byte[0];

	
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
			if(length > commonByteArray_LOCAL_PROJECT_BronzeToSilver.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_BronzeToSilver.length == 0) {
   					commonByteArray_LOCAL_PROJECT_BronzeToSilver = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_BronzeToSilver = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_LOCAL_PROJECT_BronzeToSilver, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_BronzeToSilver, 0, length, utf8Charset);
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
			if(length > commonByteArray_LOCAL_PROJECT_BronzeToSilver.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_BronzeToSilver.length == 0) {
   					commonByteArray_LOCAL_PROJECT_BronzeToSilver = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_BronzeToSilver = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_LOCAL_PROJECT_BronzeToSilver, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_BronzeToSilver, 0, length, utf8Charset);
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

		synchronized(commonByteArrayLock_LOCAL_PROJECT_BronzeToSilver) {

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

		synchronized(commonByteArrayLock_LOCAL_PROJECT_BronzeToSilver) {

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

public static class row2Struct implements routines.system.IPersistableRow<row2Struct> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_BronzeToSilver = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_BronzeToSilver = new byte[0];

	
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
			if(length > commonByteArray_LOCAL_PROJECT_BronzeToSilver.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_BronzeToSilver.length == 0) {
   					commonByteArray_LOCAL_PROJECT_BronzeToSilver = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_BronzeToSilver = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_LOCAL_PROJECT_BronzeToSilver, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_BronzeToSilver, 0, length, utf8Charset);
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
			if(length > commonByteArray_LOCAL_PROJECT_BronzeToSilver.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_BronzeToSilver.length == 0) {
   					commonByteArray_LOCAL_PROJECT_BronzeToSilver = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_BronzeToSilver = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_LOCAL_PROJECT_BronzeToSilver, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_BronzeToSilver, 0, length, utf8Charset);
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

		synchronized(commonByteArrayLock_LOCAL_PROJECT_BronzeToSilver) {

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

		synchronized(commonByteArrayLock_LOCAL_PROJECT_BronzeToSilver) {

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

public static class out1Struct implements routines.system.IPersistableRow<out1Struct> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_BronzeToSilver = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_BronzeToSilver = new byte[0];

	
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
			if(length > commonByteArray_LOCAL_PROJECT_BronzeToSilver.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_BronzeToSilver.length == 0) {
   					commonByteArray_LOCAL_PROJECT_BronzeToSilver = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_BronzeToSilver = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_LOCAL_PROJECT_BronzeToSilver, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_BronzeToSilver, 0, length, utf8Charset);
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
			if(length > commonByteArray_LOCAL_PROJECT_BronzeToSilver.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_BronzeToSilver.length == 0) {
   					commonByteArray_LOCAL_PROJECT_BronzeToSilver = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_BronzeToSilver = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_LOCAL_PROJECT_BronzeToSilver, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_BronzeToSilver, 0, length, utf8Charset);
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

		synchronized(commonByteArrayLock_LOCAL_PROJECT_BronzeToSilver) {

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

		synchronized(commonByteArrayLock_LOCAL_PROJECT_BronzeToSilver) {

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

public static class out2Struct implements routines.system.IPersistableRow<out2Struct> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_BronzeToSilver = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_BronzeToSilver = new byte[0];

	
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
			if(length > commonByteArray_LOCAL_PROJECT_BronzeToSilver.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_BronzeToSilver.length == 0) {
   					commonByteArray_LOCAL_PROJECT_BronzeToSilver = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_BronzeToSilver = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_LOCAL_PROJECT_BronzeToSilver, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_BronzeToSilver, 0, length, utf8Charset);
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
			if(length > commonByteArray_LOCAL_PROJECT_BronzeToSilver.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_BronzeToSilver.length == 0) {
   					commonByteArray_LOCAL_PROJECT_BronzeToSilver = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_BronzeToSilver = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_LOCAL_PROJECT_BronzeToSilver, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_BronzeToSilver, 0, length, utf8Charset);
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

		synchronized(commonByteArrayLock_LOCAL_PROJECT_BronzeToSilver) {

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

		synchronized(commonByteArrayLock_LOCAL_PROJECT_BronzeToSilver) {

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

public static class row1Struct implements routines.system.IPersistableRow<row1Struct> {
    final static byte[] commonByteArrayLock_LOCAL_PROJECT_BronzeToSilver = new byte[0];
    static byte[] commonByteArray_LOCAL_PROJECT_BronzeToSilver = new byte[0];

	
			    public String title;

				public String getTitle () {
					return this.title;
				}
				
			    public String channel_name;

				public String getChannel_name () {
					return this.channel_name;
				}
				
			    public String daily_rank;

				public String getDaily_rank () {
					return this.daily_rank;
				}
				
			    public String daily_movement;

				public String getDaily_movement () {
					return this.daily_movement;
				}
				
			    public String weekly_movement;

				public String getWeekly_movement () {
					return this.weekly_movement;
				}
				
			    public String snapshot_date;

				public String getSnapshot_date () {
					return this.snapshot_date;
				}
				
			    public String country;

				public String getCountry () {
					return this.country;
				}
				
			    public String view_count;

				public String getView_count () {
					return this.view_count;
				}
				
			    public String like_count;

				public String getLike_count () {
					return this.like_count;
				}
				
			    public String comment_count;

				public String getComment_count () {
					return this.comment_count;
				}
				
			    public String description;

				public String getDescription () {
					return this.description;
				}
				
			    public String thumbnail_url;

				public String getThumbnail_url () {
					return this.thumbnail_url;
				}
				
			    public String video_id;

				public String getVideo_id () {
					return this.video_id;
				}
				
			    public String channel_id;

				public String getChannel_id () {
					return this.channel_id;
				}
				
			    public String video_tags;

				public String getVideo_tags () {
					return this.video_tags;
				}
				
			    public String kind;

				public String getKind () {
					return this.kind;
				}
				
			    public String publish_date;

				public String getPublish_date () {
					return this.publish_date;
				}
				
			    public String language;

				public String getLanguage () {
					return this.language;
				}
				



	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_LOCAL_PROJECT_BronzeToSilver.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_BronzeToSilver.length == 0) {
   					commonByteArray_LOCAL_PROJECT_BronzeToSilver = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_BronzeToSilver = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_LOCAL_PROJECT_BronzeToSilver, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_BronzeToSilver, 0, length, utf8Charset);
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
			if(length > commonByteArray_LOCAL_PROJECT_BronzeToSilver.length) {
				if(length < 1024 && commonByteArray_LOCAL_PROJECT_BronzeToSilver.length == 0) {
   					commonByteArray_LOCAL_PROJECT_BronzeToSilver = new byte[1024];
				} else {
   					commonByteArray_LOCAL_PROJECT_BronzeToSilver = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_LOCAL_PROJECT_BronzeToSilver, 0, length);
			strReturn = new String(commonByteArray_LOCAL_PROJECT_BronzeToSilver, 0, length, utf8Charset);
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

		synchronized(commonByteArrayLock_LOCAL_PROJECT_BronzeToSilver) {

        	try {

        		int length = 0;
		
					this.title = readString(dis);
					
					this.channel_name = readString(dis);
					
					this.daily_rank = readString(dis);
					
					this.daily_movement = readString(dis);
					
					this.weekly_movement = readString(dis);
					
					this.snapshot_date = readString(dis);
					
					this.country = readString(dis);
					
					this.view_count = readString(dis);
					
					this.like_count = readString(dis);
					
					this.comment_count = readString(dis);
					
					this.description = readString(dis);
					
					this.thumbnail_url = readString(dis);
					
					this.video_id = readString(dis);
					
					this.channel_id = readString(dis);
					
					this.video_tags = readString(dis);
					
					this.kind = readString(dis);
					
					this.publish_date = readString(dis);
					
					this.language = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_LOCAL_PROJECT_BronzeToSilver) {

        	try {

        		int length = 0;
		
					this.title = readString(dis);
					
					this.channel_name = readString(dis);
					
					this.daily_rank = readString(dis);
					
					this.daily_movement = readString(dis);
					
					this.weekly_movement = readString(dis);
					
					this.snapshot_date = readString(dis);
					
					this.country = readString(dis);
					
					this.view_count = readString(dis);
					
					this.like_count = readString(dis);
					
					this.comment_count = readString(dis);
					
					this.description = readString(dis);
					
					this.thumbnail_url = readString(dis);
					
					this.video_id = readString(dis);
					
					this.channel_id = readString(dis);
					
					this.video_tags = readString(dis);
					
					this.kind = readString(dis);
					
					this.publish_date = readString(dis);
					
					this.language = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.title,dos);
					
					// String
				
						writeString(this.channel_name,dos);
					
					// String
				
						writeString(this.daily_rank,dos);
					
					// String
				
						writeString(this.daily_movement,dos);
					
					// String
				
						writeString(this.weekly_movement,dos);
					
					// String
				
						writeString(this.snapshot_date,dos);
					
					// String
				
						writeString(this.country,dos);
					
					// String
				
						writeString(this.view_count,dos);
					
					// String
				
						writeString(this.like_count,dos);
					
					// String
				
						writeString(this.comment_count,dos);
					
					// String
				
						writeString(this.description,dos);
					
					// String
				
						writeString(this.thumbnail_url,dos);
					
					// String
				
						writeString(this.video_id,dos);
					
					// String
				
						writeString(this.channel_id,dos);
					
					// String
				
						writeString(this.video_tags,dos);
					
					// String
				
						writeString(this.kind,dos);
					
					// String
				
						writeString(this.publish_date,dos);
					
					// String
				
						writeString(this.language,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.title,dos);
					
					// String
				
						writeString(this.channel_name,dos);
					
					// String
				
						writeString(this.daily_rank,dos);
					
					// String
				
						writeString(this.daily_movement,dos);
					
					// String
				
						writeString(this.weekly_movement,dos);
					
					// String
				
						writeString(this.snapshot_date,dos);
					
					// String
				
						writeString(this.country,dos);
					
					// String
				
						writeString(this.view_count,dos);
					
					// String
				
						writeString(this.like_count,dos);
					
					// String
				
						writeString(this.comment_count,dos);
					
					// String
				
						writeString(this.description,dos);
					
					// String
				
						writeString(this.thumbnail_url,dos);
					
					// String
				
						writeString(this.video_id,dos);
					
					// String
				
						writeString(this.channel_id,dos);
					
					// String
				
						writeString(this.video_tags,dos);
					
					// String
				
						writeString(this.kind,dos);
					
					// String
				
						writeString(this.publish_date,dos);
					
					// String
				
						writeString(this.language,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("title="+title);
		sb.append(",channel_name="+channel_name);
		sb.append(",daily_rank="+daily_rank);
		sb.append(",daily_movement="+daily_movement);
		sb.append(",weekly_movement="+weekly_movement);
		sb.append(",snapshot_date="+snapshot_date);
		sb.append(",country="+country);
		sb.append(",view_count="+view_count);
		sb.append(",like_count="+like_count);
		sb.append(",comment_count="+comment_count);
		sb.append(",description="+description);
		sb.append(",thumbnail_url="+thumbnail_url);
		sb.append(",video_id="+video_id);
		sb.append(",channel_id="+channel_id);
		sb.append(",video_tags="+video_tags);
		sb.append(",kind="+kind);
		sb.append(",publish_date="+publish_date);
		sb.append(",language="+language);
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
out1Struct out1 = new out1Struct();
out1Struct row2 = out1;
out2Struct out2 = new out2Struct();
out2Struct row3 = out2;






	
	/**
	 * [tMongoDBOutput_1 begin ] start
	 */

	

	
		
		ok_Hash.put("tMongoDBOutput_1", false);
		start_Hash.put("tMongoDBOutput_1", System.currentTimeMillis());
		
	
	currentComponent="tMongoDBOutput_1";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row2");
					}
				
		int tos_count_tMongoDBOutput_1 = 0;
		

	

        java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(java.util.logging.Level.SEVERE);

final String applicationName_tMongoDBOutput_1 = "Talend";

    int nb_line_tMongoDBOutput_1 = 0;

			class DBObjectUtil_tMongoDBOutput_1 {
				
				private org.bson.Document object = null;
				//Put value to embedded document
				//If have no embedded document, put the value to root document
				public void put(String parentNode, String curentName, Object value) {
					if (parentNode == null || "".equals(parentNode)) {
						object.put(curentName, value);
					} else {
						String objNames[]= parentNode.split("\\.");
						org.bson.Document lastNode = getParentNode(parentNode, objNames.length-1);
						lastNode.put(curentName, value);
						org.bson.Document parenttNode = null;
						for (int i = objNames.length - 1; i >=0; i--) {
							parenttNode=getParentNode(parentNode, i-1);
							parenttNode.put(objNames[i], lastNode);
							lastNode=clone(parenttNode);
						}
						object=lastNode;
					}
				}
				
				private org.bson.Document clone(org.bson.Document source){
					org.bson.Document to = new org.bson.Document();
					for(java.util.Map.Entry<String,Object> cur:source.entrySet()) {
						to.append(cur.getKey(), cur.getValue());
					}
					return to;
				}
				
				//Get node(embedded document) by path configuration
				public org.bson.Document getParentNode(String parentNode, int index) {
					org.bson.Document document = object;
					if (parentNode == null || "".equals(parentNode)) {
						return object;
					} else {
						String objNames[] = parentNode.split("\\.");
						for (int i = 0; i <= index; i++) {
							document = (org.bson.Document) document
									.get(objNames[i]);
							if (document == null) {
								document = new org.bson.Document();
								return document;
							}
							if (i == index) {
								break;
							}
						}
						return document;
					}
				}
				
				public void putkeyNode(String parentNode, String curentName, Object value){
					if (parentNode == null || "".equals(parentNode) || ".".equals(parentNode)) {
						put(parentNode, curentName, value);
					}else{
						put("", parentNode+"."+curentName, value);
					}
				}
			
				public org.bson.Document getObject() {
					return this.object;
				}
				
				public void setObject(org.bson.Document object){
					this.object=object;
				}
			
			}
            DBObjectUtil_tMongoDBOutput_1 updateObjectUtil_tMongoDBOutput_1=new DBObjectUtil_tMongoDBOutput_1();
            DBObjectUtil_tMongoDBOutput_1 queryObjectUtil_tMongoDBOutput_1=new DBObjectUtil_tMongoDBOutput_1();
            java.util.Map<String, String> pathMap_tMongoDBOutput_1=new java.util.HashMap<>();

                pathMap_tMongoDBOutput_1.put("video_id","");
                pathMap_tMongoDBOutput_1.put("view_count","");
                pathMap_tMongoDBOutput_1.put("like_count","");




    // Declarations
    com.mongodb.client.MongoClient mongo_tMongoDBOutput_1=null;
    com.mongodb.client.MongoDatabase db_tMongoDBOutput_1=null;

        mongo_tMongoDBOutput_1=(com.mongodb.client.MongoClient)globalMap.get("mongo_tMongoDBConnection_1");
        db_tMongoDBOutput_1 = (com.mongodb.client.MongoDatabase)globalMap.get("db_tMongoDBConnection_1");

        db_tMongoDBOutput_1.getCollection("data_silver_consultation").drop();
    com.mongodb.client.MongoCollection<org.bson.Document> coll_tMongoDBOutput_1 = db_tMongoDBOutput_1.getCollection("data_silver_consultation");

            List<com.mongodb.client.model.InsertOneModel<org.bson.Document>> bulkWriteOperation_tMongoDBOutput_1 = new java.util.ArrayList<>();
        int bulkWriteOperationCounter_tMongoDBOutput_1 = 1;
        final int bulkWriteOperationSize_tMongoDBOutput_1 = Integer.parseInt("50000");

 



/**
 * [tMongoDBOutput_1 begin ] stop
 */



	
	/**
	 * [tLogRow_1 begin ] start
	 */

	

	
		
		ok_Hash.put("tLogRow_1", false);
		start_Hash.put("tLogRow_1", System.currentTimeMillis());
		
	
	currentComponent="tLogRow_1";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"out1");
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
	 * [tMongoDBOutput_2 begin ] start
	 */

	

	
		
		ok_Hash.put("tMongoDBOutput_2", false);
		start_Hash.put("tMongoDBOutput_2", System.currentTimeMillis());
		
	
	currentComponent="tMongoDBOutput_2";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row3");
					}
				
		int tos_count_tMongoDBOutput_2 = 0;
		

	

        java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(java.util.logging.Level.SEVERE);

final String applicationName_tMongoDBOutput_2 = "Talend";

    int nb_line_tMongoDBOutput_2 = 0;

			class DBObjectUtil_tMongoDBOutput_2 {
				
				private org.bson.Document object = null;
				//Put value to embedded document
				//If have no embedded document, put the value to root document
				public void put(String parentNode, String curentName, Object value) {
					if (parentNode == null || "".equals(parentNode)) {
						object.put(curentName, value);
					} else {
						String objNames[]= parentNode.split("\\.");
						org.bson.Document lastNode = getParentNode(parentNode, objNames.length-1);
						lastNode.put(curentName, value);
						org.bson.Document parenttNode = null;
						for (int i = objNames.length - 1; i >=0; i--) {
							parenttNode=getParentNode(parentNode, i-1);
							parenttNode.put(objNames[i], lastNode);
							lastNode=clone(parenttNode);
						}
						object=lastNode;
					}
				}
				
				private org.bson.Document clone(org.bson.Document source){
					org.bson.Document to = new org.bson.Document();
					for(java.util.Map.Entry<String,Object> cur:source.entrySet()) {
						to.append(cur.getKey(), cur.getValue());
					}
					return to;
				}
				
				//Get node(embedded document) by path configuration
				public org.bson.Document getParentNode(String parentNode, int index) {
					org.bson.Document document = object;
					if (parentNode == null || "".equals(parentNode)) {
						return object;
					} else {
						String objNames[] = parentNode.split("\\.");
						for (int i = 0; i <= index; i++) {
							document = (org.bson.Document) document
									.get(objNames[i]);
							if (document == null) {
								document = new org.bson.Document();
								return document;
							}
							if (i == index) {
								break;
							}
						}
						return document;
					}
				}
				
				public void putkeyNode(String parentNode, String curentName, Object value){
					if (parentNode == null || "".equals(parentNode) || ".".equals(parentNode)) {
						put(parentNode, curentName, value);
					}else{
						put("", parentNode+"."+curentName, value);
					}
				}
			
				public org.bson.Document getObject() {
					return this.object;
				}
				
				public void setObject(org.bson.Document object){
					this.object=object;
				}
			
			}
            DBObjectUtil_tMongoDBOutput_2 updateObjectUtil_tMongoDBOutput_2=new DBObjectUtil_tMongoDBOutput_2();
            DBObjectUtil_tMongoDBOutput_2 queryObjectUtil_tMongoDBOutput_2=new DBObjectUtil_tMongoDBOutput_2();
            java.util.Map<String, String> pathMap_tMongoDBOutput_2=new java.util.HashMap<>();

                pathMap_tMongoDBOutput_2.put("video_id","");
                pathMap_tMongoDBOutput_2.put("video_tags","");




    // Declarations
    com.mongodb.client.MongoClient mongo_tMongoDBOutput_2=null;
    com.mongodb.client.MongoDatabase db_tMongoDBOutput_2=null;

        mongo_tMongoDBOutput_2=(com.mongodb.client.MongoClient)globalMap.get("mongo_tMongoDBConnection_1");
        db_tMongoDBOutput_2 = (com.mongodb.client.MongoDatabase)globalMap.get("db_tMongoDBConnection_1");

        db_tMongoDBOutput_2.getCollection("data_silver_tags").drop();
    com.mongodb.client.MongoCollection<org.bson.Document> coll_tMongoDBOutput_2 = db_tMongoDBOutput_2.getCollection("data_silver_tags");

            List<com.mongodb.client.model.InsertOneModel<org.bson.Document>> bulkWriteOperation_tMongoDBOutput_2 = new java.util.ArrayList<>();
        int bulkWriteOperationCounter_tMongoDBOutput_2 = 1;
        final int bulkWriteOperationSize_tMongoDBOutput_2 = Integer.parseInt("50000");

 



/**
 * [tMongoDBOutput_2 begin ] stop
 */



	
	/**
	 * [tLogRow_2 begin ] start
	 */

	

	
		
		ok_Hash.put("tLogRow_2", false);
		start_Hash.put("tLogRow_2", System.currentTimeMillis());
		
	
	currentComponent="tLogRow_2";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"out2");
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
	 * [tMap_1 begin ] start
	 */

	

	
		
		ok_Hash.put("tMap_1", false);
		start_Hash.put("tMap_1", System.currentTimeMillis());
		
	
	currentComponent="tMap_1";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row1");
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
out2Struct out2_tmp = new out2Struct();
// ###############################

        
        



        









 



/**
 * [tMap_1 begin ] stop
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


				
					com.mongodb.client.MongoCollection<org.bson.Document> coll_tMongoDBInput_1 = db_tMongoDBInput_1.getCollection("data_bronze");
				
				
				
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

						org.bson.Document myQuery_tMongoDBInput_1 = org.bson.Document.parse("{}");
						
							com.mongodb.client.FindIterable<org.bson.Document> findIterable_tMongoDBInput_1 = coll_tMongoDBInput_1.find(myQuery_tMongoDBInput_1).noCursorTimeout(false);
							


				
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
				pathMap_tMongoDBInput_1.put("title","");
				pathMap_tMongoDBInput_1.put("channel_name","");
				pathMap_tMongoDBInput_1.put("daily_rank","");
				pathMap_tMongoDBInput_1.put("daily_movement","");
				pathMap_tMongoDBInput_1.put("weekly_movement","");
				pathMap_tMongoDBInput_1.put("snapshot_date","");
				pathMap_tMongoDBInput_1.put("country","");
				pathMap_tMongoDBInput_1.put("view_count","");
				pathMap_tMongoDBInput_1.put("like_count","");
				pathMap_tMongoDBInput_1.put("comment_count","");
				pathMap_tMongoDBInput_1.put("description","");
				pathMap_tMongoDBInput_1.put("thumbnail_url","");
				pathMap_tMongoDBInput_1.put("video_id","");
				pathMap_tMongoDBInput_1.put("channel_id","");
				pathMap_tMongoDBInput_1.put("video_tags","");
				pathMap_tMongoDBInput_1.put("kind","");
				pathMap_tMongoDBInput_1.put("publish_date","");
				pathMap_tMongoDBInput_1.put("language","");

						
					com.mongodb.client.MongoCursor<org.bson.Document> cursor_tMongoDBInput_1 = findIterable_tMongoDBInput_1.iterator();
						
				while (cursor_tMongoDBInput_1.hasNext()){
				org.bson.Document o_tMongoDBInput_1 = cursor_tMongoDBInput_1.next();
				nb_line_tMongoDBInput_1++;
				Object valueObj_tMongoDBInput_1=null;
                    valueObj_tMongoDBInput_1=dbObjectInputUtil_tMongoDBInput_1.getValue(pathMap_tMongoDBInput_1.get("title"),"title",o_tMongoDBInput_1);
					
				row1.title = valueObj_tMongoDBInput_1==null ? null : valueObj_tMongoDBInput_1.toString();
				
                    valueObj_tMongoDBInput_1=dbObjectInputUtil_tMongoDBInput_1.getValue(pathMap_tMongoDBInput_1.get("channel_name"),"channel_name",o_tMongoDBInput_1);
					
				row1.channel_name = valueObj_tMongoDBInput_1==null ? null : valueObj_tMongoDBInput_1.toString();
				
                    valueObj_tMongoDBInput_1=dbObjectInputUtil_tMongoDBInput_1.getValue(pathMap_tMongoDBInput_1.get("daily_rank"),"daily_rank",o_tMongoDBInput_1);
					
				row1.daily_rank = valueObj_tMongoDBInput_1==null ? null : valueObj_tMongoDBInput_1.toString();
				
                    valueObj_tMongoDBInput_1=dbObjectInputUtil_tMongoDBInput_1.getValue(pathMap_tMongoDBInput_1.get("daily_movement"),"daily_movement",o_tMongoDBInput_1);
					
				row1.daily_movement = valueObj_tMongoDBInput_1==null ? null : valueObj_tMongoDBInput_1.toString();
				
                    valueObj_tMongoDBInput_1=dbObjectInputUtil_tMongoDBInput_1.getValue(pathMap_tMongoDBInput_1.get("weekly_movement"),"weekly_movement",o_tMongoDBInput_1);
					
				row1.weekly_movement = valueObj_tMongoDBInput_1==null ? null : valueObj_tMongoDBInput_1.toString();
				
                    valueObj_tMongoDBInput_1=dbObjectInputUtil_tMongoDBInput_1.getValue(pathMap_tMongoDBInput_1.get("snapshot_date"),"snapshot_date",o_tMongoDBInput_1);
					
				row1.snapshot_date = valueObj_tMongoDBInput_1==null ? null : valueObj_tMongoDBInput_1.toString();
				
                    valueObj_tMongoDBInput_1=dbObjectInputUtil_tMongoDBInput_1.getValue(pathMap_tMongoDBInput_1.get("country"),"country",o_tMongoDBInput_1);
					
				row1.country = valueObj_tMongoDBInput_1==null ? null : valueObj_tMongoDBInput_1.toString();
				
                    valueObj_tMongoDBInput_1=dbObjectInputUtil_tMongoDBInput_1.getValue(pathMap_tMongoDBInput_1.get("view_count"),"view_count",o_tMongoDBInput_1);
					
				row1.view_count = valueObj_tMongoDBInput_1==null ? null : valueObj_tMongoDBInput_1.toString();
				
                    valueObj_tMongoDBInput_1=dbObjectInputUtil_tMongoDBInput_1.getValue(pathMap_tMongoDBInput_1.get("like_count"),"like_count",o_tMongoDBInput_1);
					
				row1.like_count = valueObj_tMongoDBInput_1==null ? null : valueObj_tMongoDBInput_1.toString();
				
                    valueObj_tMongoDBInput_1=dbObjectInputUtil_tMongoDBInput_1.getValue(pathMap_tMongoDBInput_1.get("comment_count"),"comment_count",o_tMongoDBInput_1);
					
				row1.comment_count = valueObj_tMongoDBInput_1==null ? null : valueObj_tMongoDBInput_1.toString();
				
                    valueObj_tMongoDBInput_1=dbObjectInputUtil_tMongoDBInput_1.getValue(pathMap_tMongoDBInput_1.get("description"),"description",o_tMongoDBInput_1);
					
				row1.description = valueObj_tMongoDBInput_1==null ? null : valueObj_tMongoDBInput_1.toString();
				
                    valueObj_tMongoDBInput_1=dbObjectInputUtil_tMongoDBInput_1.getValue(pathMap_tMongoDBInput_1.get("thumbnail_url"),"thumbnail_url",o_tMongoDBInput_1);
					
				row1.thumbnail_url = valueObj_tMongoDBInput_1==null ? null : valueObj_tMongoDBInput_1.toString();
				
                    valueObj_tMongoDBInput_1=dbObjectInputUtil_tMongoDBInput_1.getValue(pathMap_tMongoDBInput_1.get("video_id"),"video_id",o_tMongoDBInput_1);
					
				row1.video_id = valueObj_tMongoDBInput_1==null ? null : valueObj_tMongoDBInput_1.toString();
				
                    valueObj_tMongoDBInput_1=dbObjectInputUtil_tMongoDBInput_1.getValue(pathMap_tMongoDBInput_1.get("channel_id"),"channel_id",o_tMongoDBInput_1);
					
				row1.channel_id = valueObj_tMongoDBInput_1==null ? null : valueObj_tMongoDBInput_1.toString();
				
                    valueObj_tMongoDBInput_1=dbObjectInputUtil_tMongoDBInput_1.getValue(pathMap_tMongoDBInput_1.get("video_tags"),"video_tags",o_tMongoDBInput_1);
					
				row1.video_tags = valueObj_tMongoDBInput_1==null ? null : valueObj_tMongoDBInput_1.toString();
				
                    valueObj_tMongoDBInput_1=dbObjectInputUtil_tMongoDBInput_1.getValue(pathMap_tMongoDBInput_1.get("kind"),"kind",o_tMongoDBInput_1);
					
				row1.kind = valueObj_tMongoDBInput_1==null ? null : valueObj_tMongoDBInput_1.toString();
				
                    valueObj_tMongoDBInput_1=dbObjectInputUtil_tMongoDBInput_1.getValue(pathMap_tMongoDBInput_1.get("publish_date"),"publish_date",o_tMongoDBInput_1);
					
				row1.publish_date = valueObj_tMongoDBInput_1==null ? null : valueObj_tMongoDBInput_1.toString();
				
                    valueObj_tMongoDBInput_1=dbObjectInputUtil_tMongoDBInput_1.getValue(pathMap_tMongoDBInput_1.get("language"),"langauge",o_tMongoDBInput_1);
					
				row1.language = valueObj_tMongoDBInput_1==null ? null : valueObj_tMongoDBInput_1.toString();
				


 



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
	 * [tMap_1 main ] start
	 */

	

	
	
	currentComponent="tMap_1";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row1"
						
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
out2 = null;


// # Output table : 'out1'
out1_tmp.video_id = row1.video_id ;
out1_tmp.view_count = row1.view_count ;
out1_tmp.like_count = row1.like_count ;
out1 = out1_tmp;

// # Output table : 'out2'
out2_tmp.video_id = row1.video_id ;
out2_tmp.video_tags = row1.video_tags ;
out2 = out2_tmp;
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
	 * [tLogRow_1 main ] start
	 */

	

	
	
	currentComponent="tLogRow_1";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"out1"
						
						);
					}
					
///////////////////////		
						



				strBuffer_tLogRow_1 = new StringBuilder();




   				
	    		if(out1.video_id != null) { //              
                    							
       
				strBuffer_tLogRow_1.append(
				                String.valueOf(out1.video_id)							
				);


							
	    		} //  			

    			strBuffer_tLogRow_1.append("|");
    			


   				
	    		if(out1.view_count != null) { //              
                    							
       
				strBuffer_tLogRow_1.append(
				                String.valueOf(out1.view_count)							
				);


							
	    		} //  			

    			strBuffer_tLogRow_1.append("|");
    			


   				
	    		if(out1.like_count != null) { //              
                    							
       
				strBuffer_tLogRow_1.append(
				                String.valueOf(out1.like_count)							
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

 
     row2 = out1;


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
	 * [tMongoDBOutput_1 main ] start
	 */

	

	
	
	currentComponent="tMongoDBOutput_1";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row2"
						
						);
					}
					

	
try{
				updateObjectUtil_tMongoDBOutput_1.setObject(new org.bson.Document());
				
				

				
                                        updateObjectUtil_tMongoDBOutput_1.put(pathMap_tMongoDBOutput_1.get("video_id"),"video_id", row2.video_id);
                                        updateObjectUtil_tMongoDBOutput_1.put(pathMap_tMongoDBOutput_1.get("view_count"),"view_count", row2.view_count);
                                        updateObjectUtil_tMongoDBOutput_1.put(pathMap_tMongoDBOutput_1.get("like_count"),"like_count", row2.like_count);
				org.bson.Document updateObj_tMongoDBOutput_1 = updateObjectUtil_tMongoDBOutput_1.getObject();
				
						if(bulkWriteOperationCounter_tMongoDBOutput_1 < bulkWriteOperationSize_tMongoDBOutput_1){
							bulkWriteOperation_tMongoDBOutput_1.add(new com.mongodb.client.model.InsertOneModel<org.bson.Document>(updateObj_tMongoDBOutput_1));
							bulkWriteOperationCounter_tMongoDBOutput_1++;
						} else {
                            bulkWriteOperation_tMongoDBOutput_1.add(new com.mongodb.client.model.InsertOneModel<org.bson.Document>(updateObj_tMongoDBOutput_1));
							coll_tMongoDBOutput_1.bulkWrite(bulkWriteOperation_tMongoDBOutput_1, new com.mongodb.client.model.BulkWriteOptions().ordered(false));
							bulkWriteOperationCounter_tMongoDBOutput_1=1;
							bulkWriteOperation_tMongoDBOutput_1 = new java.util.ArrayList<>();
						}
						
				} catch (Exception e_tMongoDBOutput_1) {
				
    					
    						System.err.println(e_tMongoDBOutput_1.getMessage());
    					
    			}
				nb_line_tMongoDBOutput_1 ++;
				
 


	tos_count_tMongoDBOutput_1++;

/**
 * [tMongoDBOutput_1 main ] stop
 */
	
	/**
	 * [tMongoDBOutput_1 process_data_begin ] start
	 */

	

	
	
	currentComponent="tMongoDBOutput_1";

	

 



/**
 * [tMongoDBOutput_1 process_data_begin ] stop
 */
	
	/**
	 * [tMongoDBOutput_1 process_data_end ] start
	 */

	

	
	
	currentComponent="tMongoDBOutput_1";

	

 



/**
 * [tMongoDBOutput_1 process_data_end ] stop
 */



	
	/**
	 * [tLogRow_1 process_data_end ] start
	 */

	

	
	
	currentComponent="tLogRow_1";

	

 



/**
 * [tLogRow_1 process_data_end ] stop
 */

} // End of branch "out1"




// Start of branch "out2"
if(out2 != null) { 



	
	/**
	 * [tLogRow_2 main ] start
	 */

	

	
	
	currentComponent="tLogRow_2";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"out2"
						
						);
					}
					
///////////////////////		
						



				strBuffer_tLogRow_2 = new StringBuilder();




   				
	    		if(out2.video_id != null) { //              
                    							
       
				strBuffer_tLogRow_2.append(
				                String.valueOf(out2.video_id)							
				);


							
	    		} //  			

    			strBuffer_tLogRow_2.append("|");
    			


   				
	    		if(out2.video_tags != null) { //              
                    							
       
				strBuffer_tLogRow_2.append(
				                String.valueOf(out2.video_tags)							
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

 
     row3 = out2;


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
	 * [tMongoDBOutput_2 main ] start
	 */

	

	
	
	currentComponent="tMongoDBOutput_2";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row3"
						
						);
					}
					

	
try{
				updateObjectUtil_tMongoDBOutput_2.setObject(new org.bson.Document());
				
				

				
                                        updateObjectUtil_tMongoDBOutput_2.put(pathMap_tMongoDBOutput_2.get("video_id"),"video_id", row3.video_id);
                                        updateObjectUtil_tMongoDBOutput_2.put(pathMap_tMongoDBOutput_2.get("video_tags"),"video_tags", row3.video_tags);
				org.bson.Document updateObj_tMongoDBOutput_2 = updateObjectUtil_tMongoDBOutput_2.getObject();
				
						if(bulkWriteOperationCounter_tMongoDBOutput_2 < bulkWriteOperationSize_tMongoDBOutput_2){
							bulkWriteOperation_tMongoDBOutput_2.add(new com.mongodb.client.model.InsertOneModel<org.bson.Document>(updateObj_tMongoDBOutput_2));
							bulkWriteOperationCounter_tMongoDBOutput_2++;
						} else {
                            bulkWriteOperation_tMongoDBOutput_2.add(new com.mongodb.client.model.InsertOneModel<org.bson.Document>(updateObj_tMongoDBOutput_2));
							coll_tMongoDBOutput_2.bulkWrite(bulkWriteOperation_tMongoDBOutput_2, new com.mongodb.client.model.BulkWriteOptions().ordered(false));
							bulkWriteOperationCounter_tMongoDBOutput_2=1;
							bulkWriteOperation_tMongoDBOutput_2 = new java.util.ArrayList<>();
						}
						
				} catch (Exception e_tMongoDBOutput_2) {
				
    					
    						System.err.println(e_tMongoDBOutput_2.getMessage());
    					
    			}
				nb_line_tMongoDBOutput_2 ++;
				
 


	tos_count_tMongoDBOutput_2++;

/**
 * [tMongoDBOutput_2 main ] stop
 */
	
	/**
	 * [tMongoDBOutput_2 process_data_begin ] start
	 */

	

	
	
	currentComponent="tMongoDBOutput_2";

	

 



/**
 * [tMongoDBOutput_2 process_data_begin ] stop
 */
	
	/**
	 * [tMongoDBOutput_2 process_data_end ] start
	 */

	

	
	
	currentComponent="tMongoDBOutput_2";

	

 



/**
 * [tMongoDBOutput_2 process_data_end ] stop
 */



	
	/**
	 * [tLogRow_2 process_data_end ] start
	 */

	

	
	
	currentComponent="tLogRow_2";

	

 



/**
 * [tLogRow_2 process_data_end ] stop
 */

} // End of branch "out2"




	
	/**
	 * [tMap_1 process_data_end ] start
	 */

	

	
	
	currentComponent="tMap_1";

	

 



/**
 * [tMap_1 process_data_end ] stop
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
	 * [tMap_1 end ] start
	 */

	

	
	
	currentComponent="tMap_1";

	


// ###############################
// # Lookup hashes releasing
// ###############################      





				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row1");
			  	}
			  	
 

ok_Hash.put("tMap_1", true);
end_Hash.put("tMap_1", System.currentTimeMillis());




/**
 * [tMap_1 end ] stop
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
			  		runStat.updateStat(resourceMap,iterateId,2,0,"out1");
			  	}
			  	
 

ok_Hash.put("tLogRow_1", true);
end_Hash.put("tLogRow_1", System.currentTimeMillis());




/**
 * [tLogRow_1 end ] stop
 */

	
	/**
	 * [tMongoDBOutput_1 end ] start
	 */

	

	
	
	currentComponent="tMongoDBOutput_1";

	

		// if bulkWriteOperationCounter_tMongoDBOutput_1 == 1 the ulkWriteOperation_tMongoDBOutput_1 is empty, do not execute.
		if (bulkWriteOperationCounter_tMongoDBOutput_1 != 1) {
			
				coll_tMongoDBOutput_1.bulkWrite(bulkWriteOperation_tMongoDBOutput_1, new com.mongodb.client.model.BulkWriteOptions().ordered(false));
				
		}
		
	globalMap.put("tMongoDBOutput_1_NB_LINE", nb_line_tMongoDBOutput_1);

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row2");
			  	}
			  	
 

ok_Hash.put("tMongoDBOutput_1", true);
end_Hash.put("tMongoDBOutput_1", System.currentTimeMillis());




/**
 * [tMongoDBOutput_1 end ] stop
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
			  		runStat.updateStat(resourceMap,iterateId,2,0,"out2");
			  	}
			  	
 

ok_Hash.put("tLogRow_2", true);
end_Hash.put("tLogRow_2", System.currentTimeMillis());




/**
 * [tLogRow_2 end ] stop
 */

	
	/**
	 * [tMongoDBOutput_2 end ] start
	 */

	

	
	
	currentComponent="tMongoDBOutput_2";

	

		// if bulkWriteOperationCounter_tMongoDBOutput_2 == 1 the ulkWriteOperation_tMongoDBOutput_2 is empty, do not execute.
		if (bulkWriteOperationCounter_tMongoDBOutput_2 != 1) {
			
				coll_tMongoDBOutput_2.bulkWrite(bulkWriteOperation_tMongoDBOutput_2, new com.mongodb.client.model.BulkWriteOptions().ordered(false));
				
		}
		
	globalMap.put("tMongoDBOutput_2_NB_LINE", nb_line_tMongoDBOutput_2);

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row3");
			  	}
			  	
 

ok_Hash.put("tMongoDBOutput_2", true);
end_Hash.put("tMongoDBOutput_2", System.currentTimeMillis());




/**
 * [tMongoDBOutput_2 end ] stop
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
	 * [tMap_1 finally ] start
	 */

	

	
	
	currentComponent="tMap_1";

	

 



/**
 * [tMap_1 finally ] stop
 */

	
	/**
	 * [tLogRow_1 finally ] start
	 */

	

	
	
	currentComponent="tLogRow_1";

	

 



/**
 * [tLogRow_1 finally ] stop
 */

	
	/**
	 * [tMongoDBOutput_1 finally ] start
	 */

	

	
	
	currentComponent="tMongoDBOutput_1";

	


 



/**
 * [tMongoDBOutput_1 finally ] stop
 */







	
	/**
	 * [tLogRow_2 finally ] start
	 */

	

	
	
	currentComponent="tLogRow_2";

	

 



/**
 * [tLogRow_2 finally ] stop
 */

	
	/**
	 * [tMongoDBOutput_2 finally ] start
	 */

	

	
	
	currentComponent="tMongoDBOutput_2";

	


 



/**
 * [tMongoDBOutput_2 finally ] stop
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
        final BronzeToSilver BronzeToSilverClass = new BronzeToSilver();

        int exitCode = BronzeToSilverClass.runJobInTOS(args);

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
            java.io.InputStream inContext = BronzeToSilver.class.getClassLoader().getResourceAsStream("local_project/bronzetosilver_0_1/contexts/" + contextStr + ".properties");
            if (inContext == null) {
                inContext = BronzeToSilver.class.getClassLoader().getResourceAsStream("config/contexts/" + contextStr + ".properties");
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

this.globalResumeTicket = true;//to run tPostJob




        end = System.currentTimeMillis();

        if (watch) {
            System.out.println((end-startTime)+" milliseconds");
        }

        endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        if (false) {
            System.out.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : BronzeToSilver");
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


    }














    private java.util.Map<String, Object> getSharedConnections4REST() {
        java.util.Map<String, Object> connections = new java.util.HashMap<String, Object>();






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
 *     105050 characters generated by Talend Open Studio for Big Data 
 *     on the 27 mars 2026, 16:43:34 CET
 ************************************************************************************************/