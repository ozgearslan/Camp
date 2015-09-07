package tr.org.lkd.lyk2015.camp.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * This entity represents an application form which is submitted by a Student.
 *
 * Same student may submit different forms in different years.
 *
 */
@Entity
public class Application extends BaseModel {

	public enum WorkStatus {
		WORKING, STUDENT, NOT_WORKING
	}

	@Min(2012)
	@Column(nullable = false)
	private Integer year;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private WorkStatus workStatus;

	@Column(nullable = false)
	private Boolean officer = false;

	private String corporation;

	@NotEmpty
	private String workDetails;

	@Column(nullable = false)
	private Integer englishLevel = 0;

	private String githubLink;

	private UUID uuid;

	@Column(nullable = false)
	private Boolean validated = false;

	@Column(nullable = false)
	private String validationId;

	@ManyToMany
	@Size(min = 1, max = 3)
	private Set<Course> preferredCourses = new HashSet<>();
	// mapped by
	// yazmadýk çünkü
	// iiki tek taraflý,
	// kursun bundan
	// haberi yok

	@ManyToOne(optional = false)
	private Student owner;

	@NotNull
	private Boolean needAccomodation;

	private Boolean isSelected;

	public WorkStatus getWorkStatus() {
		return this.workStatus;
	}

	public void setWorkStatus(WorkStatus workStatus) {
		this.workStatus = workStatus;
	}

	public boolean isOfficer() {
		return this.officer;
	}

	public void setOfficer(boolean officer) {
		this.officer = officer;
	}

	public String getCorporation() {
		return this.corporation;
	}

	public void setCorporation(String corporation) {
		this.corporation = corporation;
	}

	public String getWorkDetails() {
		return this.workDetails;
	}

	public void setWorkDetails(String workDetails) {
		this.workDetails = workDetails;
	}

	public int getEnglishLevel() {
		return this.englishLevel;
	}

	public void setEnglishLevel(int englishLevel) {
		this.englishLevel = englishLevel;
	}

	public String getGithubLink() {
		return this.githubLink;
	}

	public void setGithubLink(String githubLink) {
		this.githubLink = githubLink;
	}

	public Set<Course> getPreferredCourses() {
		return this.preferredCourses;
	}

	public void setPreferredCourses(Set<Course> preferredCourses) {
		this.preferredCourses = preferredCourses;
	}

	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Boolean getOfficer() {
		return this.officer;
	}

	public void setOfficer(Boolean officer) {
		this.officer = officer;
	}

	public Student getOwner() {
		return this.owner;
	}

	public void setOwner(Student owner) {
		this.owner = owner;
	}

	public void setEnglishLevel(Integer englishLevel) {
		this.englishLevel = englishLevel;
	}

	public UUID getUuid() {
		return this.uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public Boolean getValidated() {
		return this.validated;
	}

	public void setValidated(Boolean validated) {
		this.validated = validated;
	}

	public String getValidationId() {
		return this.validationId;
	}

	public void setValidationId(String validationId) {
		this.validationId = validationId;
	}

	public Boolean getNeedAccomodation() {
		return this.needAccomodation;
	}

	public void setNeedAccomodation(Boolean needAccomodation) {
		this.needAccomodation = needAccomodation;
	}

	public Boolean getIsSelected() {
		return this.isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
}