/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author feligomeez
 */


public class Cesta {
    private List<Mascota> mascotas;

    public Cesta() {
        mascotas = new ArrayList<>();
    }

    public void addMascota(Mascota mascota) {
        mascotas.add(mascota);
    }
    
    public void deleteMascota(Mascota mascota) {
        mascotas.remove(mascota);
    }
    
    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public void clear() {
        mascotas.clear();
    }
}
