package com.example.springBootPostgres.repository;

import com.example.springBootPostgres.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

		// Find by email
		Optional<Patient> findByEmail(String email);

		// Find by medical record number
		Optional<Patient> findByMedicalRecordNumber(String medicalRecordNumber);

		// Find all active patients
		List<Patient> findByActiveTrue();

		// Find by last name
		List<Patient> findByLastName(String lastName);

		// Find patients who have given telehealth consent
		List<Patient> findByTelehealthConsentTrue();

		// Search by first or last name
		List<Patient> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
}
