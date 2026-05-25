package com.example.springBootPostgres.controller;

import com.example.springBootPostgres.model.Patient;
import com.example.springBootPostgres.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

		@Autowired
		private PatientService patientService;

		// GET /api/patients
		@GetMapping
		public ResponseEntity<List<Patient>> getAllPatients() {
				return ResponseEntity.ok(patientService.getAllPatients());
		}

		// GET /api/patients/1
		@GetMapping("/{id}")
		public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
				return patientService.getPatientById(id)
								.map(ResponseEntity::ok)
								.orElse(ResponseEntity.notFound().build());
		}

		// GET /api/patients/email/john@example.com
		@GetMapping("/email/{email}")
		public ResponseEntity<Patient> getPatientByEmail(@PathVariable String email) {
				return patientService.getPatientByEmail(email)
								.map(ResponseEntity::ok)
								.orElse(ResponseEntity.notFound().build());
		}

		// GET /api/patients/mrn/MRN-001
		@GetMapping("/mrn/{mrn}")
		public ResponseEntity<Patient> getPatientByMrn(@PathVariable String mrn) {
				return patientService.getPatientByMrn(mrn)
								.map(ResponseEntity::ok)
								.orElse(ResponseEntity.notFound().build());
		}

		// GET /api/patients/search?name=john
		@GetMapping("/search")
		public ResponseEntity<List<Patient>> searchPatients(@RequestParam String name) {
				return ResponseEntity.ok(patientService.searchPatients(name));
		}

		// GET /api/patients/telehealth
		@GetMapping("/telehealth")
		public ResponseEntity<List<Patient>> getTelehealthPatients() {
				return ResponseEntity.ok(patientService.getTelehealthPatients());
		}

		// POST /api/patients
		@PostMapping
		public ResponseEntity<?> createPatient(@RequestBody Patient patient) {
				try {
						Patient created = patientService.createPatient(patient);
						return ResponseEntity.status(HttpStatus.CREATED).body(created);
				} catch (RuntimeException e) {
						return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
				}
		}

		// PUT /api/patients/1
		@PutMapping("/{id}")
		public ResponseEntity<?> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
				try {
						Patient updated = patientService.updatePatient(id, patient);
						return ResponseEntity.ok(updated);
				} catch (RuntimeException e) {
						return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
				}
		}

		// DELETE /api/patients/1
		@DeleteMapping("/{id}")
		public ResponseEntity<?> deletePatient(@PathVariable Long id) {
				try {
						patientService.deletePatient(id);
						return ResponseEntity.ok("Patient deactivated successfully.");
				} catch (RuntimeException e) {
						return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
				}
		}
}