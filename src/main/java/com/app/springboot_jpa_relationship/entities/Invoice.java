package com.app.springboot_jpa_relationship.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Long total;

    /*
     * La primer palabra de la carnalidad se refiere a la clase donde esta el
     * atributo (Invoice) y la siguiente palabra de la carnalidad se refiere al
     * atributo (a la clase) que se está inyectando
     * 
     * En Spring Boot y JPA (Java Persistence API), las anotaciones de relaciones de
     * entidades (carnalidades) se utilizan para definir cómo se relacionan las
     * entidades en una base de datos. Las principales anotaciones de relaciones
     * son @ManyToOne, @OneToMany, @OneToOne, y @ManyToMany.
     * 
     * Anotación @ManyToOne
     * La anotación @ManyToOne se usa para especificar una relación de muchos a uno
     * entre dos entidades. Esto significa que múltiples instancias de una entidad
     * pueden estar asociadas con una única instancia de otra entidad. En términos
     * prácticos, se utiliza para establecer una relación donde muchas filas de una
     * tabla están vinculadas a una única fila de otra tabla.
     * 
     * En este ejemplo:
     * 
     * Invoice es una entidad que representa una factura.
     * Client es una entidad que representa un cliente.
     * Cada Invoice tiene un Client asociado, pero un Client puede estar asociado
     * con múltiples Invoices.
     * La anotación @ManyToOne en el campo client de Invoice indica que múltiples
     * facturas pueden estar asociadas con un único cliente.
     * 
     * @OneToMany
     * La anotación @OneToMany se usa para especificar una relación de uno a muchos.
     * Es el inverso de @ManyToOne.
     * 
     * @OneToOne
     * La anotación @OneToOne se usa para especificar una relación de uno a uno,
     * donde una instancia de una entidad está asociada con una única instancia de
     * otra entidad.
     * 
     * @ManyToMany
     * La anotación @ManyToMany se usa para especificar una relación de muchos a
     * muchos, donde múltiples instancias de una entidad pueden estar asociadas con
     * múltiples instancias de otra entidad.
     */
    @ManyToOne
    /*
     * La anotación @JoinColumn se usa en JPA para especificar el nombre de la
     * columna que se utiliza para establecer una relación entre dos entidades en
     * una base de datos. Básicamente, define la columna en la tabla de la entidad
     * propietaria que se usará como la clave foránea para referenciar la entidad
     * relacionada.
     * 
     * Uso de @JoinColumn
     * Cuando se define una relación de entidades, la anotación @JoinColumn permite
     * especificar detalles sobre cómo se mapea esa relación en la base de datos.
     * Estos detalles incluyen el nombre de la columna que actuará como clave
     * foránea, si la columna puede ser nula, y cómo se comporta la clave foránea en
     * términos de inserción y actualización.
     */
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
    @JoinColumn(name = "client_id")
    private Client client;

    public Invoice(String description, Long total) {
        this.description = description;
        this.total = total;
    }

    public Invoice() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", description='" + getDescription() + "'" +
                ", total='" + getTotal() + "'" +
                "}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((total == null) ? 0 : total.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Invoice other = (Invoice) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (total == null) {
            if (other.total != null)
                return false;
        } else if (!total.equals(other.total))
            return false;
        return true;
    }

}
