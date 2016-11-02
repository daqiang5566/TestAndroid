package com.example.testandroid.database.entity;

import com.example.testandroid.database.annotation.Column;
import com.example.testandroid.database.annotation.ColumnType;
import com.example.testandroid.database.annotation.Id;
import com.example.testandroid.database.annotation.Table;

@Table(name="user_info")
public class UserInfo extends BaseEntity {
	
	@Id
	@Column(name = "userid", type = ColumnType.INTEGER, inSimple = true)
	private long userid;

	@Column(name = "username", type = ColumnType.TEXT, inSimple = true)
	private String username;

	@Column(name = "shead", type = ColumnType.TEXT, inSimple = true)
	private String shead;
	
	@Column(name = "sex", type = ColumnType.INTEGER, inSimple = true)
	private int sex;
	
	@Column(name = "birthday", type = ColumnType.TEXT, inSimple = true)
	private String birthday;

	@Column(name = "prov", type = ColumnType.TEXT, inSimple = true)
	private String prov;

	@Column(name = "city", type = ColumnType.TEXT, inSimple = true)
	private String city;
}


