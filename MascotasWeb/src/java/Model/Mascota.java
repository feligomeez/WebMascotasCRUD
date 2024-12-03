/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;
/**
 *
 * @author feligomeez
 */
@Entity
@Table(name = "Mascotas")
@NamedQueries({
    @NamedQuery(name = "Mascota.findAll", query = "SELECT m FROM Mascota m"),
    @NamedQuery(name = "Mascota.findByName", query = "SELECT m FROM Mascota m WHERE m.nombre =:nombre")
})
public class Mascota implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mascotaID;
    @ManyToOne
    @JoinColumn(name = "usuarioID")
    private Usuario usuario;

    private String nombre;
    @Enumerated(EnumType.STRING)  // Mapea el enum como un String
    @Column(name = "Especie")
    private Especie especie;
    private String raza;
    
    @Enumerated(EnumType.STRING)  // Mapea el enum como un String
    @Column(name = "Genero")
    private Genero genero;
    
    private float peso;
    private String alergias;

    public enum Especie {
        Perro,
        Gato,
        Pez,
        Huron,
        Ave
    }
    
    public enum Genero {
        Macho, Hembra
    }

    public Mascota() {
    }
    
    public Mascota(Long id, String nombre, Especie especie, String raza, Genero genero, float peso, String alergias) {
        this.mascotaID = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.genero = genero;
        this.peso = peso;
        this.alergias = alergias;
    }
    
    public Mascota(String nombre, Especie especie, String raza, Genero genero, float peso, String alergias) {
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.genero = genero;
        this.peso = peso;
        this.alergias = alergias;
    }
    
    public Mascota(String nombre, Especie especie, String raza, Genero genero, float peso, String alergias, Usuario usuario) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.genero = genero;
        this.peso = peso;
        this.alergias = alergias;
    }
    
    
    
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public Long getMascotaID() {
        return mascotaID;
    }

    public void setMascotaID(Long mascotaID) {
        this.mascotaID = mascotaID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mascotaID != null ? mascotaID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the mascotaID fields are not set
        if (!(object instanceof Mascota)) {
            return false;
        }
        Mascota other = (Mascota) object;
        if ((this.mascotaID == null && other.mascotaID != null) || (this.mascotaID != null && !this.mascotaID.equals(other.mascotaID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Mascota[ id=" + mascotaID + " ]";
    }

}
