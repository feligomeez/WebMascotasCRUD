/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author feligomeez
 */
@Entity
@Table(name="Usuarios")
@NamedQueries({
 @NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u"),
 @NamedQuery(name="Usuario.findByName", query="SELECT u FROM Usuario u WHERE u.nombre = :nombre"),
})
public class Usuario implements Serializable { 

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="usuarioID")
    private Long usuarioID;
    
    private String nombre;
    private String apellidos;
    private String email;
    @Column(nullable = false)
    private String password;
    private String telefono;
    private String direccion;
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE, fetch=FetchType.EAGER)
    private List<Mascota> mascotas;
    private String provincia;
    private String localidad;
    
    public Usuario() {
    }

    public Usuario(String nombre, String apellidos, String email, String password, String telefono, String direccion) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.password = password;
    }

    public Usuario(String nombre, String apellidos, String email, String password, String telefono, String direccion, String provincia, String localidad) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.direccion = direccion;
        this.provincia = provincia;
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }
    
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public Long getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(Long usuarioID) {
        this.usuarioID = usuarioID;
    }
    
    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(List<Mascota> mascotas) {
        this.mascotas = mascotas;
    }
    
    public void addMascota(Mascota mascota){
        mascota.setUsuario(this);
        this.mascotas.add(mascota);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioID != null ? usuarioID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the usuarioID fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usuarioID == null && other.usuarioID != null) || (this.usuarioID != null && !this.usuarioID.equals(other.usuarioID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Usuario[ id=" + usuarioID + " ]";
    }
    
}
