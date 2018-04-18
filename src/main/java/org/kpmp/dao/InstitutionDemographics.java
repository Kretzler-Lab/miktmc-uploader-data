package org.kpmp.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "institution_demographics")
public class InstitutionDemographics {

	@Id
	@Column(name = "id")
	private int id;
	@Column(name = "inst_name")
	private String institutionName;
	@Column(name = "inst_shortname")
	private String institutionShortName;

	public String getInstitutionShortName() {
		return institutionShortName;
	}

	public void setInstitutionShortName(String institutionShortName) {
		this.institutionShortName = institutionShortName;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
