package com.tarang.ewallet.initialdatasetup;
import java.util.HashMap;

public interface SchedulerScriptOracleDataBase {

	String QRTZ_BLOB_TRIGGERS ="CREATE TABLE qrtz_blob_triggers(" +
											"sched_name varchar2(120) NOT NULL," +
											" trigger_name varchar2(200) NOT NULL," +
											" trigger_group varchar2(200) NOT NULL," +
											" blob_data BLOB)";
	
	String QRTZ_CALENDARS ="CREATE TABLE qrtz_calendars(" +
											"sched_name varchar2(120) NOT NULL," +
											" calendar_name varchar2(200) NOT NULL," +
											" calendar BLOB NOT NULL)";
	
	String QRTZ_CRON_TRIGGERS = "CREATE TABLE qrtz_cron_triggers(" +
											"sched_name varchar2(120) NOT NULL, " +
											"trigger_name varchar2(200) NOT NULL, " +
											"trigger_group varchar2(200) NOT NULL, " +
											"cron_expression varchar2(120) NOT NULL, " +
											"time_zone_id varchar2(80))";
	
	String QRTZ_FIRED_TRIGGERS = "CREATE TABLE qrtz_fired_triggers(" +
											"sched_name varchar2(120) NOT NULL, " +
											"entry_id varchar2(95) NOT NULL, " +
											"trigger_name varchar2(200) NOT NULL, " +
											"trigger_group varchar2(200) NOT NULL, " +
											"instance_name varchar2(200) NOT NULL, " +
											"fired_time numeric NOT NULL, " +
											"priority numeric NOT NULL, " +
											"state varchar2(16) NOT NULL, " +
											"job_name varchar2(200), " +
											"job_group varchar2(200), " +
											"is_nonconcurrent number(1), " +
											"requests_recovery number(1))";
	
	String QRTZ_JOB_DETAILS = "CREATE TABLE qrtz_job_details(" +
											"sched_name varchar2(120) NOT NULL, " +
											"job_name varchar2(200) NOT NULL, " +
											"job_group varchar2(200) NOT NULL, " +
											"description varchar2(250), " +
											"job_class_name varchar2(250) NOT NULL, " +
											"is_durable number(1) NOT NULL, " +
											"is_nonconcurrent number(1) NOT NULL, " +
											"is_update_data number(1) NOT NULL, " +
											"requests_recovery number(1) NOT NULL, " +
											"job_data BLOB)";
	
	String QRTZ_LOCKS = "CREATE TABLE qrtz_locks(" +
											"sched_name varchar2(120) NOT NULL," +
											" lock_name varchar2(40) NOT NULL)";
	
	String QRTZ_PAUSED_TRIGGER_GRPS = "CREATE TABLE qrtz_paused_trigger_grps(" +
											"sched_name varchar2(120) NOT NULL, " +
											"trigger_group varchar2(200) NOT NULL)";
	
	String QRTZ_SCHEDULER_STATE = "CREATE TABLE qrtz_scheduler_state(" +
											"sched_name varchar2(120) NOT NULL," +
											"instance_name varchar2(200) NOT NULL," +
											" last_checkin_time numeric NOT NULL, " +
											"checkin_interval numeric NOT NULL)";
	
	String QRTZ_SIMPLE_TRIGGERS = "CREATE TABLE qrtz_simple_triggers(" +
											"sched_name varchar2(120) NOT NULL, " +
											"trigger_name varchar2(200) NOT NULL, " +
											"trigger_group varchar2(200) NOT NULL, " +
											"repeat_count numeric NOT NULL,  " +
											"repeat_interval numeric NOT NULL, " +
											"times_triggered numeric NOT NULL)";
	
	String QRTZ_SIMPROP_TRIGGERS = "CREATE TABLE qrtz_simprop_triggers(" +
											"sched_name varchar2(120) NOT NULL, " +
											"trigger_name varchar2(200) NOT NULL, " +
											"trigger_group varchar2(200) NOT NULL, " +
											"str_prop_1 varchar2(512), " +
											"str_prop_2 varchar2(512), " +
											"str_prop_3 varchar2(512), " +
											"int_prop_1 integer, " +
											"int_prop_2 integer, " +
											"long_prop_1 numeric, " +
											"long_prop_2 numeric, " +
											"dec_prop_1 numeric(13,4), " +
											"dec_prop_2 numeric(13,4), " +
											"bool_prop_1 numeric(1), " +
											"bool_prop_2 numeric(1))";
	
	String 	QRTZ_TRIGGERS = "CREATE TABLE qrtz_triggers(" +
											"sched_name varchar2(120) NOT NULL, " +
											"trigger_name varchar2(200) NOT NULL, " +
											"trigger_group varchar2(200) NOT NULL, " +
											"job_name varchar2(200) NOT NULL, " +
											"job_group varchar2(200) NOT NULL, " +
											"description varchar2(250), " +
											"next_fire_time numeric, " +
											"prev_fire_time numeric, " +
											"priority integer, " +
											"trigger_state varchar2(16) NOT NULL, " +
											"trigger_type varchar2(8) NOT NULL, " +
											"start_time numeric NOT NULL, " +
											"end_time numeric, " +
											"calendar_name varchar2(200), " +
											"misfire_instr numeric, " +
											"job_data BLOB)";
	
	
	HashMap<String, String > scriptsMap = new HashMap<String, String>(){
		private static final long serialVersionUID = 1L;
		{
	        put("QRTZ_BLOB_TRIGGERS",QRTZ_BLOB_TRIGGERS);
	        put("QRTZ_CALENDARS",QRTZ_CALENDARS);
	        put("QRTZ_CRON_TRIGGERS",QRTZ_CRON_TRIGGERS);
	        put("QRTZ_FIRED_TRIGGERS",QRTZ_FIRED_TRIGGERS);
	        put("QRTZ_JOB_DETAILS",QRTZ_JOB_DETAILS);
	        put("QRTZ_LOCKS",QRTZ_LOCKS);
	        put("QRTZ_PAUSED_TRIGGER_GRPS",QRTZ_PAUSED_TRIGGER_GRPS);
	        put("QRTZ_SCHEDULER_STATE",QRTZ_SCHEDULER_STATE);
	        put("QRTZ_SIMPLE_TRIGGERS",QRTZ_SIMPLE_TRIGGERS);
	        put("QRTZ_SIMPROP_TRIGGERS",QRTZ_SIMPROP_TRIGGERS);
	        put("QRTZ_TRIGGERS",QRTZ_TRIGGERS);
	    }
	};

}