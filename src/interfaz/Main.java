package interfaz;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Pantalla pantalla = new Pantalla();
            pantalla.mostrar(); // Cambiado de setVisible(true) a mostrar()
        });
    }
}