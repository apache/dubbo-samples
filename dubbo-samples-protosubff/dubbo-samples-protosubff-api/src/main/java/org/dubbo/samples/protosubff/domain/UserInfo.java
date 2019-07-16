package org.dubbo.samples.protosubff.domain;

public class UserInfo {

	private String uuid;
	
	private String name;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "UserInfo [uuid=" + uuid + ", name=" + name + "]";
	}
	
	
}
