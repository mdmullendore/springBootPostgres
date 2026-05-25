package com.example.springBootPostgres.service;

import com.example.springBootPostgres.model.Patient;
import com.example.springBootPostgres.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

		@Autowired
		private PatientRepository patientRepository;

		// ─── Get All Patients ─────────────────────────────────────────────
		public List<Patient> getAllPatients() {
				return patientRepository.findByActiveTrue();
		}

		// ─── Get Patient By ID ────────────────────────────────────────────
		public Optional<Patient> getPatientById(Long id) {
				return patientRepository.findById(id);
		}

		// ─── Get Patient By Email ─────────────────────────────────────────
		public Optional<Patient> getPatientByEmail(String email) {
				return patientRepository.findByEmail(email);
		}

		// ─── Get Patient By MRN ───────────────────────────────────────────
		public Optional<Patient> getPatientByMrn(String mrn) {
				return patientRepository.findByMedicalRecordNumber(mrn);
		}

		// ─── Search Patients by Name ──────────────────────────────────────
		public List<Patient> searchPatients(String name) {
				return patientRepository.findByFirstNameContainingOrLastNameContaining(name, name);
		}

		// ─── Get Telehealth Consented Patients ────────────────────────────
		public List<Patient> getTelehealthPatients() {
				return patientRepository.findByTelehealthConsentTrue();
		}

		// ─── Create Patient ───────────────────────────────────────────────
		public Patient createPatient(Patient patient) {
				// Check if email already exists
				if (patientRepository.findByEmail(patient.getEmail()).isPresent()) {
						throw new RuntimeException("A patient with this email already exists.");
				}
				return patientRepository.save(patient);
		}

		// ─── Update Patient ───────────────────────────────────────────────
		public Patient updatePatient(Long id, Patient updatedPatient) {
				Patient existing = patientRepository.findById(id)
								.orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));

				existing.setFirstName(updatedPatient.getFirstName());
				existing.setLastName(updatedPatient.getLastName());
				existing.setEmail(updatedPatient.getEmail());
				existing.setPhoneNumber(updatedPatient.getPhoneNumber());
				existing.setDateOfBirth(updatedPatient.getDateOfBirth());
				existing.setGender(updatedPatient.getGender());
				existing.setAddressLine1(updatedPatient.getAddressLine1());
				existing.setAddressLine2(updatedPatient.getAddressLine2());
				existing.setCity(updatedPatient.getCity());
				existing.setState(updatedPatient.getState());
				existing.setZipCode(updatedPatient.getZipCode());
				existing.setCountry(updatedPatient.getCountry());
				existing.setBloodType(updatedPatient.getBloodType());
				existing.setAllergies(updatedPatient.getAllergies());
				existing.setCurrentMedications(updatedPatient.getCurrentMedications());
				existing.setMedicalHistory(updatedPatient.getMedicalHistory());
				existing.setNotes(updatedPatient.getNotes());
				existing.setInsuranceProvider(updatedPatient.getInsuranceProvider());
				existing.setInsurancePolicyNumber(updatedPatient.getInsurancePolicyNumber());
				existing.setInsuranceGroupNumber(updatedPatient.getInsuranceGroupNumber());
				existing.setEmergencyContactName(updatedPatient.getEmergencyContactName());
				existing.setEmergencyContactPhone(updatedPatient.getEmergencyContactPhone());
				existing.setEmergencyContactRelationship(updatedPatient.getEmergencyContactRelationship());
				existing.setTelehealthConsent(updatedPatient.isTelehealthConsent());
				existing.setPreferredVideoProvider(updatedPatient.getPreferredVideoProvider());
				existing.setAppointmentPreference(updatedPatient.getAppointmentPreference());

				return patientRepository.save(existing);
		}

		// ─── Soft Delete Patient ──────────────────────────────────────────
		public void deletePatient(Long id) {
				Patient patient = patientRepository.findById(id)
								.orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));

				// Soft delete — never hard delete patient records in healthcare
				patient.setActive(false);
				patientRepository.save(patient);
		}
}