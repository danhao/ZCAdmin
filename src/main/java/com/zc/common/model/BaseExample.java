package com.zc.common.model;

import java.io.Serializable;

public abstract class BaseExample implements Serializable {
	private static final long serialVersionUID = 2439791710707683013L;
	private Integer start;
	private Integer pageSize;
	private String tableName;
	private int pid;// 玩家player_id，主要是为了切换数据源
	private String playerId;// 玩家player_id，主要是为了切换数据源
	private int dataSource;// 指定的数据源
	private String zone;// 纵横四海分区

	private Integer itemId;
	private String itemName;

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getDataSource() {
		return dataSource;
	}

	public void setDataSource(int dataSource) {
		this.dataSource = dataSource;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public abstract void setOrderByClause(String orderByClause);
}
