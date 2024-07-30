package com.app.springboot_jpa_relationship;

import java.util.Optional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.app.springboot_jpa_relationship.entities.Address;
import com.app.springboot_jpa_relationship.entities.Client;
import com.app.springboot_jpa_relationship.entities.ClientDetails;
import com.app.springboot_jpa_relationship.entities.Course;
import com.app.springboot_jpa_relationship.entities.Invoice;
import com.app.springboot_jpa_relationship.entities.Student;
import com.app.springboot_jpa_relationship.repositories.ClientDetailsRepository;
import com.app.springboot_jpa_relationship.repositories.ClientRepository;
import com.app.springboot_jpa_relationship.repositories.CourseRepository;
import com.app.springboot_jpa_relationship.repositories.InvoiceRepository;
import com.app.springboot_jpa_relationship.repositories.StudentRepository;

@SpringBootApplication
public class SpringbootJpaRelationshipApplication implements CommandLineRunner {

	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private ClientDetailsRepository clientDetailsRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationshipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// manyToOneFindByIdClient();
		// oneToManyFindById();
		// removeAddress();
		// removeAddressFindById();
		// oneToManyInvoiceBidirectionalFindById();
		// manyToOne();
		// oneToMany();
		// removeInvoiceBidirectionalFindById();
		// removeInvoiceBidirectional();
		// oneToOne();
		// oneToOneFindById();
		// oneToOneBidirectionalFindById();
		// manyToManyRemoveCourseUnidirectional();
		// manyToManyRemoveBidirectional();
		manyToManyRemoveBidirectionalFind();
	}

	@Transactional
	public void manyToManyBidirectional() {
		Student student1 = new Student("Jano", "Pura");
		Student student2 = new Student("Erba", "Doe");

		Course course1 = new Course("Java", "Pepito");
		Course course2 = new Course("C++", "Juanito");

		student1.addCourse(course1);
		student1.addCourse(course2);
		student2.addCourse(course2);

		List<Student> students = (List<Student>) studentRepository.saveAll(Set.of(student1, student2));
		students.forEach(student -> {
			System.out.println(student + "\n");
		});
	}

	@Transactional
	public void manyToManyRemoveBidirectional() {
		Student student1 = new Student("Jano", "Pura");
		Student student2 = new Student("Erba", "Doe");

		Course course1 = new Course("Java", "Pepito");
		Course course2 = new Course("C++", "Juanito");

		student1.addCourse(course1);
		student1.addCourse(course2);
		student2.addCourse(course2);

		List<Student> students = (List<Student>) studentRepository.saveAll(Set.of(student1, student2));
		students.forEach(student -> {
			System.out.println(student + "\n");
		});

		Optional<Student> studentOptionalDB = studentRepository.findOneWithCourse(3L);
		studentOptionalDB.ifPresentOrElse(studentDB -> {
			Optional<Course> courseOptionalDb = courseRepository.findOneWithStudents(2L);
			courseOptionalDb.ifPresentOrElse(courseDB -> {
				studentDB.removeCourse(courseDB);
				Student updateStudent = studentRepository.save(studentDB);
				System.out.println(updateStudent);
			}, () -> System.out.println("Curso no encontrado"));
		}, () -> System.out.println("Estudiante no encontrado"));
	}

	@Transactional
	public void manyToManyRemoveBidirectionalFind() {
		Optional<Student> optionalStudent1 = studentRepository.findOneWithCourse(1L);
		Optional<Student> optionalStudent2 = studentRepository.findOneWithCourse(2L);

		Student student1 = optionalStudent1.get();
		Student student2 = optionalStudent2.get();

		Course course1 = new Course("Java", "Pepito");
		Course course2 = new Course("C++", "Juanito");

		courseRepository.saveAll(Set.of(course1, course2));

		Optional<Course> optionalCourse1 = courseRepository.findById(course1.getId());
		Optional<Course> optionalCourse2 = courseRepository.findById(course2.getId());

		Course savedCourse1 = optionalCourse1.get();
		Course savedCourse2 = optionalCourse2.get();

		student1.setCourses(Set.of(savedCourse1, savedCourse2));
		student2.setCourses(Set.of(savedCourse2));

		// student1.addCourse(course1);
		// student1.addCourse(course2);
		// student2.addCourse(course2);

		List<Student> students = (List<Student>) studentRepository.saveAll(Set.of(student1, student2));
		students.forEach(student -> {
			System.out.println(student + "\n");
		});

		Optional<Student> studentOptionalDB = studentRepository.findOneWithCourse(1L);
		studentOptionalDB.ifPresentOrElse(studentDB -> {
			Optional<Course> courseOptionalDb = courseRepository.findOneWithStudents(2L);
			courseOptionalDb.ifPresentOrElse(courseDB -> {
				studentDB.removeCourse(courseDB);
				Student updateStudent = studentRepository.save(studentDB);
				System.out.println(updateStudent);
			}, () -> System.out.println("Curso no encontrado"));
		}, () -> System.out.println("Estudiante no encontrado"));
	}

	@Transactional
	public void manyToMany() {
		Student student1 = new Student("Jano", "Pura");
		Student student2 = new Student("Erba", "Doe");

		Course course1 = new Course("Java", "Pepito");
		Course course2 = new Course("C++", "Juanito");

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		List<Student> students = (List<Student>) studentRepository.saveAll(Set.of(student1, student2));
		students.forEach(student -> {
			System.out.println(student + "\n");
		});
	}

	/*
	 * Aquí hubo un error y el código realizado en manyToMany no aplica exactamente
	 * para este método, ya que duplicaba los cursos que se persistian dos veces en
	 * un estudiante, cada vez que se llama a new Course(), estas nuevas instancias
	 * no están siendo reconocidas como la misma entidad por el contexto de
	 * persistencia de JPA/Hibernate. Por lo tanto, Hibernate las trata como nuevas
	 * entidades y las inserta en la base de datos con nuevos IDs.
	 * 
	 * En este código, primero guardamos los cursos en la base de datos y luego los
	 * recuperamos para asegurarnos de que estamos asignando las mismas instancias a
	 * los estudiantes. Esto evita que Hibernate cree nuevas entradas para los
	 * cursos ya existentes.
	 * 
	 * en una relación Many-to-Many, incluso si usas CascadeType.PERSIST o
	 * CascadeType.MERGE, Hibernate no verificará automáticamente si las entidades
	 * relacionadas ya existen en la base de datos. Esto significa que, si creas
	 * nuevas instancias de las entidades relacionadas y las asignas a la entidad
	 * propietaria de la relación, Hibernate las tratará como nuevas entidades y las
	 * insertará en la base de datos, resultando en duplicados.
	 */
	@Transactional
	public void manyToManyFindById() {
		Optional<Student> optionalStudent1 = studentRepository.findById(1L);
		Optional<Student> optionalStudent2 = studentRepository.findById(2L);

		Student student1 = optionalStudent1.get();
		Student student2 = optionalStudent2.get();

		Course course1 = new Course("Java", "Pepito");
		Course course2 = new Course("C++", "Juanito");

		courseRepository.saveAll(Set.of(course1, course2));

		Optional<Course> optionalCourse1 = courseRepository.findById(course1.getId());
		Optional<Course> optionalCourse2 = courseRepository.findById(course2.getId());

		Course savedCourse1 = optionalCourse1.get();
		Course savedCourse2 = optionalCourse2.get();

		student1.setCourses(Set.of(savedCourse1, savedCourse2));
		student2.setCourses(Set.of(savedCourse2));

		List<Student> students = (List<Student>) studentRepository.saveAll(Set.of(student1, student2));
		students.forEach(student -> {
			System.out.println(student + "\n");
		});
	}

	@Transactional
	public void manyToManyRemoveFind() {
		Optional<Student> optionalStudent1 = studentRepository.findById(1L);
		Optional<Student> optionalStudent2 = studentRepository.findById(2L);

		Student student1 = optionalStudent1.get();
		Student student2 = optionalStudent2.get();

		Course course1 = new Course("Java", "Pepito");
		Course course2 = new Course("C++", "Juanito");

		courseRepository.saveAll(Set.of(course1, course2));

		Optional<Course> optionalCourse1 = courseRepository.findById(course1.getId());
		Optional<Course> optionalCourse2 = courseRepository.findById(course2.getId());

		Course savedCourse1 = optionalCourse1.get();
		Course savedCourse2 = optionalCourse2.get();

		student1.setCourses(Set.of(savedCourse1, savedCourse2));
		student2.setCourses(Set.of(savedCourse2));

		List<Student> students = (List<Student>) studentRepository.saveAll(Set.of(student1, student2));
		students.forEach(student -> {
			System.out.println(student + "\n");
		});

		Optional<Student> studentOptionalDB = studentRepository.findOneWithCourse(1L);
		studentOptionalDB.ifPresentOrElse(studentDB -> {
			Optional<Course> courseOptionalDb = courseRepository.findById(2L);
			courseOptionalDb.ifPresentOrElse(courseDB -> {
				studentDB.getCourses().remove(courseDB);
				Student updateStudent = studentRepository.save(studentDB);
				System.out.println(updateStudent);
			}, () -> System.out.println("Curso no encontrado"));
		}, () -> System.out.println("Estudiante no encontrado"));
	}

	@Transactional
	public void manyToManyRemove() {
		Student student1 = new Student("Jano", "Pura");
		Student student2 = new Student("Erba", "Doe");

		Course course1 = new Course("Java", "Pepito");
		Course course2 = new Course("C++", "Juanito");

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		List<Student> students = (List<Student>) studentRepository.saveAll(Set.of(student1, student2));
		students.forEach(student -> {
			System.out.println(student + "\n");
		});

		Optional<Student> studentOptionalDB = studentRepository.findOneWithCourse(3L);
		studentOptionalDB.ifPresentOrElse(studentDB -> {
			Optional<Course> courseOptionalDb = courseRepository.findById(2L);
			courseOptionalDb.ifPresentOrElse(courseDB -> {
				studentDB.getCourses().remove(courseDB);
				Student updateStudent = studentRepository.save(studentDB);
				System.out.println(updateStudent);
			}, () -> System.out.println("Curso no encontrado"));
		}, () -> System.out.println("Estudiante no encontrado"));
	}

	@Transactional
	public void manyToManyRemoveCourseUnidirectional() {
		Course course1 = new Course("Java", "Pepito");
		Course course2 = new Course("C++", "Juanito");

		courseRepository.saveAll(Set.of(course1, course2));

		Optional<Course> optionalCourse1 = courseRepository.findById(course1.getId());
		Optional<Course> optionalCourse2 = courseRepository.findById(course2.getId());

		Course savedCourse1 = optionalCourse1.get();
		Course savedCourse2 = optionalCourse2.get();

		Optional<Student> student1 = studentRepository.findById(1L);
		Optional<Student> student2 = studentRepository.findById(2L);

		student1.ifPresent(student -> student.setCourses(Set.of(savedCourse1, savedCourse2)));
		student2.ifPresent(student -> student.setCourses(Set.of(savedCourse1)));

		List<Student> students = (List<Student>) studentRepository.saveAll(List.of(student1.get(), student2.get()));

		students.forEach(student -> {
			System.out.println(student + "\n");
		});

		Optional<Course> courseOptionalDB = courseRepository.findById(2L);
		courseOptionalDB.ifPresent(courseRepository::deleteCourseAndRelationships);

	}

	@Transactional
	public void oneToOneBidirectional() {
		Client client = new Client("Erba", "Pura");

		ClientDetails clientDetails = new ClientDetails(true, 5000);
		client.setClientDetails(clientDetails);

		Client clientDB = clientRepository.save(client);

		System.out.println(clientDB);
	}

	@Transactional
	public void oneToOneBidirectionalFindById() {
		Optional<Client> optionalClient = clientRepository.findOne(1L);
		optionalClient.ifPresentOrElse(client -> {
			ClientDetails clientDetails = new ClientDetails(true, 5000);
			client.setClientDetails(clientDetails);

			Client clientDB = clientRepository.save(client);

			System.out.println(clientDB);
		}, () -> System.out.println("Cliente no encontrado"));
	}

	@Transactional
	public void oneToOne() {
		ClientDetails clientDetails = new ClientDetails(true, 5000);
		clientDetailsRepository.save(clientDetails);

		Client client = new Client("Erba", "Pura");
		client.setClientDetails(clientDetails);
		Client clientDB = clientRepository.save(client);
		System.out.println(clientDB);
	}

	@Transactional
	public void oneToOneFindById() {
		ClientDetails clientDetails = new ClientDetails(true, 5000);
		clientDetailsRepository.save(clientDetails);

		Optional<Client> clientOptional = clientRepository.findOne(2L);
		clientOptional.ifPresentOrElse(client -> {
			client.setClientDetails(clientDetails);
			Client clientDB = clientRepository.save(client);
			System.out.println(clientDB);
		},
				() -> System.out.println("Cliente no encontrado"));
	}

	@Transactional
	public void oneToManyInvoiceBidirectional() {
		Client client = new Client("Fran", "Moras");
		Invoice invoice1 = new Invoice("Compras de la casa", 5000L);
		Invoice invoice2 = new Invoice("Compras de oficina", 8000L);

		client.addInvoice(invoice1).addInvoice(invoice2);

		clientRepository.save(client);

		System.out.println(client);
	}

	@Transactional
	public void oneToManyInvoiceBidirectionalFindById() {
		Optional<Client> optionalClient = clientRepository.findOne(1L);
		optionalClient.ifPresentOrElse(client -> {
			Invoice invoice1 = new Invoice("Compras de la casa", 5000L);
			Invoice invoice2 = new Invoice("Compras de oficina", 8000L);

			client.addInvoice(invoice1).addInvoice(invoice2);

			Client clientDB = clientRepository.save(client);

			System.out.println(clientDB);
		}, () -> {
			System.out.println("Cliente no encontrado");
		});
	}

	@Transactional
	public void removeInvoiceBidirectionalFindById() {
		Optional<Client> optionalClient = clientRepository.findOne(1L);
		optionalClient.ifPresentOrElse(client -> {
			Invoice invoice1 = new Invoice("Compras de la casa", 5000L);
			Invoice invoice2 = new Invoice("Compras de oficina", 8000L);

			client.addInvoice(invoice1).addInvoice(invoice2);

			Client clientDB = clientRepository.save(client);

			System.out.println(clientDB);
		}, () -> {
			System.out.println("Cliente no encontrado");
		});

		Optional<Client> optionalCient2 = clientRepository.findOne(1L);
		optionalCient2.ifPresentOrElse(client -> {
			Optional<Invoice> invoiceOptional = invoiceRepository.findById(2L);
			invoiceOptional.ifPresentOrElse(invoice -> {
				client.removeInvoice(invoice);
				Client updateCLient = clientRepository.save(client);
				System.out.println(updateCLient);
			}, () -> System.out.println("Factura no encontrada"));

		}, () -> System.out.println("Cliente no encontrado"));
	}

	@Transactional
	public void removeInvoiceBidirectional() {
		Optional<Client> optionalClient = Optional.of(new Client("Fran", "Moras"));
		optionalClient.ifPresentOrElse(client -> {
			Invoice invoice1 = new Invoice("Compras de la casa", 5000L);
			Invoice invoice2 = new Invoice("Compras de oficina", 8000L);

			client.addInvoice(invoice1).addInvoice(invoice2);

			Client clientDB = clientRepository.save(client);

			System.out.println(clientDB);
		}, () -> {
			System.out.println("Cliente no encontrado");
		});

		Optional<Client> optionalCient2 = clientRepository.findOne(3L);
		optionalCient2.ifPresentOrElse(client -> {
			Optional<Invoice> invoiceOptional = invoiceRepository.findById(2L);
			invoiceOptional.ifPresentOrElse(invoice -> {
				client.removeInvoice(invoice);
				Client updateCLient = clientRepository.save(client);
				System.out.println(updateCLient);
			}, () -> System.out.println("Factura no encontrada"));

		}, () -> System.out.println("Cliente no encontrado"));
	}

	@Transactional
	public void oneToMany() {
		Client client = new Client("Fran", "Moras");
		Address address1 = new Address("Constitucion", 211);
		Address address2 = new Address("Revolucion", 127);

		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		/**
		 * Al guardar el cliente, también se guardarán automáticamente las direcciones
		 * asociadas.
		 * Esto se debe a que la relación @OneToMany entre Client y Address tiene el
		 * argumento
		 * cascade = CascadeType.ALL.
		 * 
		 * La cascada incluye la operación PERSIST, por lo que cuando se guarda un nuevo
		 * cliente,
		 * sus direcciones asociadas se insertarán automáticamente en la base de datos.
		 * 
		 * Además, la operación REMOVE también está incluida en la cascada. Esto
		 * significa que,
		 * al eliminar un cliente, se eliminarán automáticamente sus direcciones
		 * asociadas.
		 * 
		 * Al usar cascade = CascadeType.ALL en la relación @OneToMany, se
		 * asegura que
		 * todas las operaciones de persistencia, actualización y eliminación se
		 * propaguen desde
		 * el cliente a sus direcciones, manteniendo la consistencia de la base de
		 * datos.
		 */
		clientRepository.save(client);

		System.out.println(client);
	}

	@Transactional
	public void oneToManyFindById() {
		Optional<Client> optionalClient = clientRepository.findById(2L);
		optionalClient.ifPresent(client -> {
			Address address1 = new Address("Constitucion", 211);
			Address address2 = new Address("Revolucion", 127);

			Set<Address> addresses = new HashSet<>();
			addresses.add(address1);
			addresses.add(address2);

			client.setAddresses(addresses);

			Client clientDB = clientRepository.save(client);

			System.out.println(clientDB);
		});

	}

	@Transactional
	public void removeAddress() {
		// Creación de un cliente y dos direcciones
		Client client = new Client("Fran", "Moras");
		Address address1 = new Address("Constitucion", 211);
		Address address2 = new Address("Revolucion", 127);

		// Se agregan las direcciones a la lista de direcciones del cliente
		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		// Se guarda el cliente junto con sus direcciones en la base de datos
		// Gracias a la cascada (CascadeType.ALL), las direcciones se persisten
		// automáticamente
		clientRepository.save(client);

		System.out.println("Cliente guardado: " + client);

		// Se busca el cliente en la base de datos por su ID
		Optional<Client> optionalClient = clientRepository.findById(3L);
		optionalClient.ifPresent(c -> {
			// Eliminación de la dirección de la lista de direcciones del cliente
			// Esto solo elimina la dirección de la lista en la memoria, no en la base de
			// datos
			c.getAddresses().remove(address1);

			/*
			 * Se guarda el cliente actualizado en la base de datos
			 * Esto elimina la dirección de la base de datos gracias a orphanRemoval = true
			 * Es necesario que las entidades tengan implementados los métodos equals y
			 * hashCode
			 * para que la eliminación funcione correctamente, en este caso el criterio de
			 * busqueda es por el ID y no por la instancia en memoria
			 * Al tener el orphanRemoval en true elimina las direcciones huérfanas, si fuera
			 * false solo eliminaría de la tabla de relaciones, más no de tabla Dirección
			 * como tal
			 */
			Client clientDB = clientRepository.save(c);
			System.out.println("Cliente actualizado: " + clientDB);
		});
	}

	@Transactional
	public void removeAddressFindById() {
		Optional<Client> optionalClient = clientRepository.findById(2L);
		optionalClient.ifPresent(client -> {
			// Crear nuevas direcciones y agregarlas al cliente
			Address address1 = new Address("Constitucion", 211);
			Address address2 = new Address("Revolucion", 127);

			client.getAddresses().add(address1);
			client.getAddresses().add(address2);

			// Guardar el cliente y obtener IDs para las nuevas direcciones
			Client client2 = clientRepository.save(client);
			System.out.println(client2);

			// Recuperar el cliente de la base de datos para asegurarse de que todas las
			// entidades tengan IDs
			Optional<Client> optionalClient2 = clientRepository.findById(2L);
			optionalClient2.ifPresent(c -> {
				// Recuperar las direcciones persistentes
				Address persistedAddress1 = c.getAddresses().stream()
						.filter(a -> a.getStreet().equals("Constitucion") && a.getNumber().equals(211))
						.findFirst()
						.orElse(null);

				if (persistedAddress1 != null) {
					c.getAddresses().remove(persistedAddress1);
					clientRepository.save(c);
				}

				System.out.println(c);
			});
		});
	}

	@Transactional
	public void manyToOne() {
		Client client = new Client("John", "Doe");
		clientRepository.save(client);

		Invoice invoice = new Invoice("compras de oficina", 2000L);
		invoice.setClient(client);
		Invoice invoiceDB = invoiceRepository.save(invoice);
		System.out.println(invoiceDB);
	}

	@Transactional
	public void manyToOneFindByIdClient() {
		Optional<Client> optionalClient = clientRepository.findById(1L);
		if (optionalClient.isPresent()) {
			Client client = optionalClient.orElseThrow();

			Invoice invoice = new Invoice("compras de oficina", 2000L);
			invoice.setClient(client);
			Invoice invoiceDB = invoiceRepository.save(invoice);
			System.out.println(invoiceDB);
		}
	}

}
