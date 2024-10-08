package org.miktmc.users;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class UserJsonMixin {

	@JsonIgnore
	abstract String getId();

	@JsonIgnore
	abstract String getShibId();

}
