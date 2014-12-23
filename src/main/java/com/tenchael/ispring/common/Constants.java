package com.tenchael.ispring.common;

public class Constants {
	public static final Integer DEFAULT_PAGE = 0;
	public static final Integer DEFAULT_PAGE_SIZE = 20;
	public static final Integer LOG_DEFAULT_LIST_SIZE = 5;
	public static final String CODE = "code";
	public static final String MESSAGE = "message";
	public static final Integer OP_SUCCESS = 1;
	public static final Integer OP_FAILED = -1;
	public static final String OPRT = "oprt";
	public static final String CREATE = "create";
	public static final String UPDATE = "update";
	public static final String EDIT = "edit";
	
	
	/**
	 * 当前系统的文件目录分割符
	 */
	public static final String FILE_SEPARATOR = System.getProperty("file.separator");

	/**
	 * 当前系统文件编码
	 */
	public static final String FILE_ENCODING = System.getProperty("file.encoding");

	/**
	 * 当前OS的换行符
	 */
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
}
