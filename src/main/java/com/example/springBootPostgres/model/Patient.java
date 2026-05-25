package com.example.springBootPostgres.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "patients")
public class Patient {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		// ─── Personal Information ───────────────────────────────────────
		@Column(nullable = false)
		private String firstName;

		@Column(nullable = false)
		private String lastName;

		@Column(nullable = false, unique = true)
		private String email;

		@Column(nullable = false)
		private String phoneNumber;

		@Column(nullable = false)
		private LocalDate dateOfBirth;

		@Enumerated(EnumType.STRING)
		private Gender gender;

		// ─── Address ────────────────────────────────────────────────────
		private String addressLine1;
		private String addressLine2;
		private String city;
		private String state;
		private String zipCode;
		private String country;

		// ─── Medical Information ─────────────────────────────────────────
		@Column(unique = true)
		private String medicalRecordNumber;   // MRN

		@Enumerated(EnumType.STRING)
		private BloodType bloodType;

		@Column(columnDefinition = "TEXT")
		private String allergies;

		@Column(columnDefinition = "TEXT")
		private String currentMedications;

		@Column(columnDefinition = "TEXT")
		private String medicalHistory;

		@Column(columnDefinition = "TEXT")
		private String notes;

		// ─── Insurance ───────────────────────────────────────────────────
		private String insuranceProvider;
		private String insurancePolicyNumber;
		private String insuranceGroupNumber;

		// ─── Emergency Contact ───────────────────────────────────────────
		private String emergencyContactName;
		private String emergencyContactPhone;
		private String emergencyContactRelationship;

		// ─── Telehealth ──────────────────────────────────────────────────
		@Column(nullable = false)
		private boolean telehealthConsent = false;

		private String preferredVideoProvider;    // e.g. "Zoom", "Doxy.me"

		@Enumerated(EnumType.STRING)
		private AppointmentPreference appointmentPreference;  // IN_PERSON, TELEHEALTH, NO_PREFERENCE

		// ─── Account / Audit ─────────────────────────────────────────────
		@Column(nullable = false, updatable = false)
		private LocalDateTime createdAt;

		@Column(nullable = false)
		private LocalDateTime updatedAt;

		@Column(nullable = false)
		private boolean active = true;

		// ─── Enums ───────────────────────────────────────────────────────
		public enum Gender {
				MALE, FEMALE, NON_BINARY, PREFER_NOT_TO_SAY, OTHER
		}

		public enum BloodType {
				A_POSITIVE, A_NEGATIVE,
				B_POSITIVE, B_NEGATIVE,
				AB_POSITIVE, AB_NEGATIVE,
				O_POSITIVE, O_NEGATIVE,
				UNKNOWN
		}

		public enum AppointmentPreference {
				IN_PERSON, TELEHEALTH, NO_PREFERENCE
		}

		// ─── Lifecycle Hooks ─────────────────────────────────────────────
		@PrePersist
		protected void onCreate() {
				createdAt = LocalDateTime.now();
				updatedAt = LocalDateTime.now();
		}

		@PreUpdate
		protected void onUpdate() {
				updatedAt = LocalDateTime.now();
		}

		// ─── Constructors ─────────────────────────────────────────────────
		public Patient() {}

		public Patient(String firstName, String lastName, String email,
										String phoneNumber, LocalDate dateOfBirth, Gender gender) {
				this.firstName = firstName;
				this.lastName = lastName;
				this.email = email;
				this.phoneNumber = phoneNumber;
				this.dateOfBirth = dateOfBirth;
				this.gender = gender;
		}

		// ─── Getters & Setters ────────────────────────────────────────────
		public Long getId() { return id; }

		public String getFirstName() { return firstName; }
		public void setFirstName(String firstName) { this.firstName = firstName; }

		public String getLastName() { return lastName; }
		public void setLastName(String lastName) { this.lastName = lastName; }

		public String getEmail() { return email; }
		public void setEmail(String email) { this.email = email; }

		public String getPhoneNumber() { return phoneNumber; }
		public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

		public LocalDate getDateOfBirth() { return dateOfBirth; }
		public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

		public Gender getGender() { return gender; }
		public void setGender(Gender gender) { this.gender = gender; }

		public String getAddressLine1() { return addressLine1; }
		public void setAddressLine1(String addressLine1) { this.addressLine1 = addressLine1; }

		public String getAddressLine2() { return addressLine2; }
		public void setAddressLine2(String addressLine2) { this.addressLine2 = addressLine2; }

		public String getCity() { return city; }
		public void setCity(String city) { this.city = city; }

		public String getState() { return state; }
		public void setState(String state) { this.state = state; }

		public String getZipCode() { return zipCode; }
		public void setZipCode(String zipCode) { this.zipCode = zipCode; }

		public String getCountry() { return country; }
		public void setCountry(String country) { this.country = country; }

		public String getMedicalRecordNumber() { return medicalRecordNumber; }
		public void setMedicalRecordNumber(String medicalRecordNumber) { this.medicalRecordNumber = medicalRecordNumber; }

		public BloodType getBloodType() { return bloodType; }
		public void setBloodType(BloodType bloodType) { this.bloodType = bloodType; }

		public String getAllergies() { return allergies; }
		public void setAllergies(String allergies) { this.allergies = allergies; }

		public String getCurrentMedications() { return currentMedications; }
		public void setCurrentMedications(String currentMedications) { this.currentMedications = currentMedications; }

		public String getMedicalHistory() { return medicalHistory; }
		public void setMedicalHistory(String medicalHistory) { this.medicalHistory = medicalHistory; }

		public String getNotes() { return notes; }
		public void setNotes(String notes) { this.notes = notes; }

		public String getInsuranceProvider() { return insuranceProvider; }
		public void setInsuranceProvider(String insuranceProvider) { this.insuranceProvider = insuranceProvider; }

		public String getInsurancePolicyNumber() { return insurancePolicyNumber; }
		public void setInsurancePolicyNumber(String insurancePolicyNumber) { this.insurancePolicyNumber = insurancePolicyNumber; }

		public String getInsuranceGroupNumber() { return insuranceGroupNumber; }
		public void setInsuranceGroupNumber(String insuranceGroupNumber) { this.insuranceGroupNumber = insuranceGroupNumber; }

		public String getEmergencyContactName() { return emergencyContactName; }
		public void setEmergencyContactName(String emergencyContactName) { this.emergencyContactName = emergencyContactName; }

		public String getEmergencyContactPhone() { return emergencyContactPhone; }
		public void setEmergencyContactPhone(String emergencyContactPhone) { this.emergencyContactPhone = emergencyContactPhone; }

		public String getEmergencyContactRelationship() { return emergencyContactRelationship; }
		public void setEmergencyContactRelationship(String emergencyContactRelationship) { this.emergencyContactRelationship = emergencyContactRelationship; }

		public boolean isTelehealthConsent() { return telehealthConsent; }
		public void setTelehealthConsent(boolean telehealthConsent) { this.telehealthConsent = telehealthConsent; }

		public String getPreferredVideoProvider() { return preferredVideoProvider; }
		public void setPreferredVideoProvider(String preferredVideoProvider) { this.preferredVideoProvider = preferredVideoProvider; }

		public AppointmentPreference getAppointmentPreference() { return appointmentPreference; }
		public void setAppointmentPreference(AppointmentPreference appointmentPreference) { this.appointmentPreference = appointmentPreference; }

		public LocalDateTime getCreatedAt() { return createdAt; }
		public LocalDateTime getUpdatedAt() { return updatedAt; }

		public boolean isActive() { return active; }
		public void setActive(boolean active) { this.active = active; }
}