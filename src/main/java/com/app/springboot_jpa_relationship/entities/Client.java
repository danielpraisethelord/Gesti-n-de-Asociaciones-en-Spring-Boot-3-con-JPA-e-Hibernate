package com.app.springboot_jpa_relationship.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;

    /**
     * @OneToMany
     *            La anotación @OneToMany se usa para especificar una relación de
     *            uno a muchos.
     *            Es el inverso de @ManyToOne.
     * 
     *            En JPA (Java Persistence API), el argumento cascade en las
     *            anotaciones de relaciones
     *            (@OneToMany, @ManyToOne, @OneToOne, @ManyToMany) se utiliza para
     *            especificar qué operaciones de persistencia deben propagarse
     *            automáticamente desde una entidad hacia sus entidades
     *            relacionadas. Esto es especialmente útil para gestionar
     *            automáticamente la persistencia, actualización, eliminación, etc.,
     *            de entidades relacionadas sin necesidad de hacerlo manualmente.
     * 
     *            Tipos de Cascade
     *            Los tipos de CascadeType son:
     * 
     *            CascadeType.ALL: Propaga todas las operaciones (persist, merge,
     *            remove, refresh, detach).
     *            CascadeType.PERSIST: Propaga la operación de persistencia
     *            (guardar) a las entidades relacionadas.
     *            CascadeType.MERGE: Propaga la operación de merge (actualización) a
     *            las entidades relacionadas.
     *            CascadeType.REMOVE: Propaga la operación de eliminación a las
     *            entidades relacionadas.
     *            CascadeType.REFRESH: Propaga la operación de refresh (recargar el
     *            estado desde la base de datos) a las entidades relacionadas.
     *            CascadeType.DETACH: Propaga la operación de detach (desasociar la
     *            entidad del contexto de persistencia) a las entidades
     *            relacionadas.
     * 
     *            El atributo orphanRemoval en las anotaciones de relaciones de JPA
     *            (@OneToMany, @OneToOne) se utiliza para indicar que los
     *            "huérfanos" (entidades relacionadas que ya no están asociadas con
     *            la entidad propietaria) deben ser eliminados automáticamente de la
     *            base de datos. Esto es útil para mantener la integridad
     *            referencial y evitar entradas huérfanas en la base de datos.
     */
    /*
     * Si no se especifica una columna de unión (@JoinColumn) en una
     * relación @OneToMany en JPA, Hibernate crea una tabla intermedia para
     * gestionar la relación entre las dos entidades.
     * 
     * Relación @OneToMany Sin @JoinColumn
     * Cuando defines una relación @OneToMany en JPA sin especificar @JoinColumn,
     * Hibernate puede crear una tabla intermedia para gestionar esta relación,
     * especialmente cuando se usa una colección no estándar como Set o Map. Sin
     * embargo, si utilizas una List, por defecto se espera una columna de clave
     * foránea en la tabla de la entidad "muchos". Pero en algunas configuraciones,
     * especialmente si no hay un mapeo bidireccional bien definido, Hibernate puede
     * decidir crear una tabla intermedia.
     * 
     * Explicación del Funcionamiento
     * Tabla Intermedia (clients_addresses):
     * 
     * Se crea una tabla clients_addresses con dos columnas: addresses_id y
     * client_id.
     * addresses_id referencia a la clave primaria de la tabla addresses.
     * client_id referencia a la clave primaria de la tabla clients.
     * Propagación de Cambios:
     * 
     * Cuando se guarda un Client, se inserta una entrada en la tabla clients.
     * Se insertan las direcciones en la tabla addresses.
     * Se insertan las relaciones en la tabla clients_addresses para asociar cada
     * dirección con su cliente correspondiente.
     * Uso de CascadeType.ALL y orphanRemoval:
     * 
     * CascadeType.ALL asegura que todas las operaciones de persistencia se
     * propaguen automáticamente desde Client a sus Address asociadas.
     * orphanRemoval = true asegura que si una Address se elimina de la lista
     * addresses de un Client, esta se eliminará automáticamente de la base de
     * datos.
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    /*
     * Al especificar el JoinColumn va a crear solamente la tabla de direcciones que
     * va a contener la llave foranea client_id
     */
    // @JoinColumn(name = "client_id")
    /*
     * Explicación de @JoinTable
     * La anotación @JoinTable se utiliza para definir la tabla intermedia que se
     * emplea para gestionar la relación entre dos entidades en JPA. Aunque es más
     * común en relaciones @ManyToMany, también puede utilizarse en
     * relaciones @OneToMany para definir explícitamente una tabla intermedia.
     * 
     * Componentes de @JoinTable
     * name: El nombre de la tabla intermedia.
     * joinColumns: Define la columna en la tabla intermedia que se refiere a la
     * entidad propietaria de la relación.
     * inverseJoinColumns: Define la columna en la tabla intermedia que se refiere a
     * la entidad que está siendo referenciada.
     * uniqueConstraints: (Opcional) Define restricciones de unicidad en las
     * columnas de la tabla intermedia.
     * 
     * Desglose del Ejemplo
     * 
     * @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true):
     * 
     * Define una relación de uno a muchos entre Client y Address.
     * cascade = CascadeType.ALL asegura que todas las operaciones de persistencia
     * se propaguen desde el Client a sus Address asociadas.
     * orphanRemoval = true garantiza que si una Address se elimina de la lista
     * addresses, también se eliminará de la base de datos.
     * 
     * @JoinTable:
     * 
     * name = "tbl_clientes_to_direcciones": Especifica que el nombre de la tabla
     * intermedia será tbl_clientes_to_direcciones.
     * joinColumns = @JoinColumn(name = "id_cliente"): Define la columna en la tabla
     * intermedia (tbl_clientes_to_direcciones) que se refiere a la clave primaria
     * de la entidad Client (id_cliente).
     * inverseJoinColumns = @JoinColumn(name = "id_direcciones"): Define la columna
     * en la tabla intermedia (tbl_clientes_to_direcciones) que se refiere a la
     * clave primaria de la entidad Address (id_direcciones).
     * uniqueConstraints = @UniqueConstraint(columnNames = {"id_direcciones"}):
     * Establece una restricción de unicidad en la columna id_direcciones,
     * asegurando que cada Address esté asociada con un solo Client.
     */
    @JoinTable(name = "tbl_clientes_to_direcciones", joinColumns = @JoinColumn(name = "id_cliente"), inverseJoinColumns = @JoinColumn(name = "id_direcciones"), uniqueConstraints = @UniqueConstraint(columnNames = {
            "id_direcciones" }))
    /*
     * Aquí usamos mejor Set que List
     * La razón por la cual Set no produce el error mientras que List sí lo hace
     * cuando se utilizan múltiples fetch simultáneos en Hibernate se debe a cómo
     * Hibernate maneja las colecciones y su incapacidad para manejar múltiples bag
     * fetches en una sola consulta.
     * 
     * Bag Fetching en Hibernate
     * En Hibernate, un bag es una colección no ordenada de elementos que permite
     * duplicados, y es mapeada generalmente con List en Java. Cuando se realiza un
     * fetch de un bag, Hibernate genera una consulta que recupera los elementos del
     * bag en una sola operación de selección, pero no puede manejar múltiples bag
     * fetches en una única consulta correctamente.
     * 
     * Problemas con List (Bag)
     * Cuando tienes múltiples asociaciones de tipo List y las recuperas
     * simultáneamente con fetch join, Hibernate no sabe cómo manejar esto
     * eficientemente y genera una excepción del tipo
     * org.hibernate.loader.MultipleBagFetchException. Esto se debe a que Hibernate
     * no puede distinguir adecuadamente entre las filas resultantes de las
     * múltiples asociaciones List (bag), y esto causa problemas en la
     * desambiguación de los datos cargados.
     * 
     * Uso de Set (No Bag)
     * Por otro lado, cuando utilizas Set, Hibernate trata estas colecciones como
     * set fetch en lugar de bag fetch. Un set en Hibernate es una colección sin
     * duplicados y no tiene el mismo problema de desambiguación que un bag. Por lo
     * tanto, Hibernate puede manejar múltiples set fetches en una sola consulta sin
     * problemas.
     * 
     * Resumen Teórico
     * Bag (List): Es una colección que permite duplicados y no tiene un orden
     * específico. Hibernate no maneja bien múltiples bag fetches debido a problemas
     * de desambiguación en las filas resultantes de la consulta.
     * Set: Es una colección que no permite duplicados. Hibernate puede manejar
     * múltiples set fetches sin problemas, ya que no enfrenta el mismo problema de
     * desambiguación que con los bags.
     * 
     * Set y List son dos interfaces en Java que representan colecciones de
     * elementos, pero tienen diferentes características y usos. Aquí están las
     * principales diferencias:
     * 
     * Set
     * No permite duplicados: Un Set no permite elementos duplicados. Cada elemento
     * en el Set debe ser único.
     * 
     * No tiene orden definido: La mayoría de las implementaciones de Set no
     * mantienen el orden de los elementos. Sin embargo, hay excepciones como
     * LinkedHashSet, que mantiene el orden de inserción, y TreeSet, que mantiene
     * los elementos ordenados de acuerdo a su orden natural o un comparador
     * proporcionado.
     * 
     * Implementaciones comunes:
     * 
     * HashSet: La implementación más común, no garantiza el orden de los elementos.
     * LinkedHashSet: Mantiene el orden de inserción.
     * TreeSet: Mantiene los elementos ordenados de acuerdo a su orden natural o un
     * comparador específico.
     * Uso: Set es ideal cuando necesitas garantizar que no haya elementos
     * duplicados y no te importa el orden de los elementos.
     * 
     * List
     * Permite duplicados: Un List permite elementos duplicados. Puedes tener
     * múltiples elementos con el mismo valor.
     * 
     * Mantiene el orden de inserción: Un List mantiene el orden en que los
     * elementos fueron insertados. Puedes acceder a los elementos por su posición
     * (índice).
     * 
     * Implementaciones comunes:
     * 
     * ArrayList: La implementación más común, proporciona acceso rápido por índice
     * pero es más lenta para operaciones de inserción y eliminación en el medio de
     * la lista.
     * LinkedList: Implementa una lista doblemente enlazada, proporcionando
     * inserciones, eliminaciones y acceso secuencial más rápidos, pero es más lenta
     * para acceso aleatorio por índice.
     * Vector: Similar a ArrayList pero es sincronizada y por lo tanto, segura para
     * su uso en múltiples hilos. Sin embargo, es menos eficiente debido a la
     * sobrecarga de la sincronización.
     * Uso: List es ideal cuando necesitas mantener el orden de los elementos,
     * permitir duplicados, y necesitas acceder a los elementos por su posición.
     * 
     * Resumen de Diferencias
     * Duplicados:
     * 
     * Set: No permite duplicados.
     * List: Permite duplicados.
     * Orden de los elementos:
     * 
     * Set: No garantiza el orden (con excepciones como LinkedHashSet y TreeSet).
     * List: Mantiene el orden de inserción.
     * Acceso a los elementos:
     * 
     * Set: No tiene índices, no se puede acceder a los elementos por posición.
     * List: Se puede acceder a los elementos por su posición utilizando índices.
     * Implementaciones comunes:
     * 
     * Set: HashSet, LinkedHashSet, TreeSet.
     * List: ArrayList, LinkedList, Vector.
     */
    private Set<Address> addresses;

    /*
     * A nivel de programación, a nivel de JPA, de Java, de clases, la relación se
     * lleva en ambos lados, (En Invoice es ManyToOne con CLient), pero el dueño de
     * relación a nivel de BDD es Invoice, para especificar eso usamos el JoinColumn
     * en Inovice
     * En la tabla Facturas está la FK, por lo tanto es el dueño de la relación
     * Y en la contraparte se tiene que especificar la relación inversa (mappedBy,
     * se especifica el atributo de la contraparte)
     * 
     * De esta manera se estructura una relación inversa, por un lado en el
     * OneToMany definimos el mappedBy con el atributo o la relación inversa de la
     * otra clase, la contraparte, la cuál sería Inovice, ya que tiene el atributo
     * Client
     */

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client")
    private Set<Invoice> invoices;

    /* Clase padre */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client")
    private ClientDetails clientDetails;

    /**
     * En una relación de tipo OneToMany, la lista de entidades relacionadas
     * debe estar inicializada. Es preferible hacerlo en el constructor vacío
     * para evitar problemas de null cuando se agreguen elementos a la lista.
     */
    public Client() {
        addresses = new HashSet<>();
        invoices = new HashSet<>();
    }

    public Client(String name, String lastname) {
        this();
        this.name = name;
        this.lastname = lastname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    public ClientDetails getClientDetails() {
        return clientDetails;
    }

    public void setClientDetails(ClientDetails clientDetails) {
        this.clientDetails = clientDetails;
        clientDetails.setClient(this);
    }

    public void removeClientDetails(ClientDetails clientDetails) {
        clientDetails.setClient(this);
        this.clientDetails = null;
    }

    public Client addInvoice(Invoice invoice) {
        invoices.add(invoice);
        invoice.setClient(this);
        return this;
    }

    public void removeInvoice(Invoice invoice) {
        this.getInvoices().remove(invoice);
        invoice.setClient(null);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", name='" + getName() + "'" +
                ", lastname='" + getLastname() + "'" +
                ", addresses='" + getAddresses() + "'" +
                ", invoices='" + getInvoices() + "'" +
                ", clientDetails='" + getClientDetails() + "'" +
                "}";
    }

}
