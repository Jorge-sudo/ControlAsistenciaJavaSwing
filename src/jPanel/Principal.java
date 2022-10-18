
package jPanel;

import Estudiante_TablaDeSQL.Conexion;
import com.raven.chart.ModelChart;
import com.raven.model.Model_Card;
import java.awt.Color;
import static java.awt.image.ImageObserver.HEIGHT;
import java.sql.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


public class Principal extends javax.swing.JPanel {

    public Principal() {
        initComponents();
        int a = Tutores();
        String aux = String.valueOf(a);
        card1.setData(new Model_Card(new ImageIcon(getClass().getResource("/com/raven/icon/stock.png")), "Profesores", "3", "Asistencia puntual"));
        card2.setData(new Model_Card(new ImageIcon(getClass().getResource("/com/raven/icon/profit.png")), "Tutores", aux, "Asistencia puntual"));
        init();
        System.out.println(Tutores());
    }
    public int Tutores() {
        int id;
        int aux = 0;
        try {
            String sql = "SELECT * FROM tutor;";
            Connection cn = Conexion.getConection();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                id = rs.getInt(1);
                if(id > aux){
                    aux = id;
                }
            }


        } catch (SQLException ex) {
            mensajeError(" Error en la conexion con la base de datos");
        }
        return aux;
    }
    
    void mensajeInformacion(String msje) {
        JOptionPane.showMessageDialog(null, msje, "Informacion", JOptionPane.INFORMATION_MESSAGE);
    }

    void mensajeError(String msje) {
        JOptionPane.showMessageDialog(null, msje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    void mensajeExito(String msje) {
        JOptionPane.showMessageDialog(null, msje, "Operacion Exitosa", JOptionPane.PLAIN_MESSAGE, new ImageIcon("src/com/images/exito.png"));
    }

    void mensajeAdvertencia(String msje) {
        JOptionPane.showMessageDialog(null, msje, "Advertencia", HEIGHT);
    }
    
     private void init() {
        chart.addLegend("Income", new Color(12, 84, 175), new Color(0, 108, 247));
        chart.addLegend("Expense", new Color(54, 4, 143), new Color(104, 49, 200));
        chart.addLegend("Profit", new Color(5, 125, 0), new Color(95, 209, 69));
        chart.addLegend("Cost", new Color(186, 37, 37), new Color(241, 100, 120));
        chart.addData(new ModelChart("January", new double[]{500, 200, 80, 89}));
        chart.addData(new ModelChart("February", new double[]{600, 750, 90, 150}));
        chart.addData(new ModelChart("March", new double[]{200, 350, 460, 900}));
        chart.addData(new ModelChart("April", new double[]{480, 150, 750, 700}));
        chart.addData(new ModelChart("May", new double[]{350, 540, 300, 150}));
        chart.addData(new ModelChart("June", new double[]{190, 280, 81, 200}));
        chart.start();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        card1 = new com.raven.component.Card();
        card2 = new com.raven.component.Card();
        chart = new com.raven.chart.Chart();
        jLabel3 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(242, 242, 242));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Cambria", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 52, 0));
        jLabel1.setText("Bienvenido al Control de Asistencia");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(129, 6, -1, 29));

        jLabel2.setFont(new java.awt.Font("Cambria", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 52, 0));
        jLabel2.setText("Kinder \"PATITAS SUAVES\"");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(196, 41, 445, 34));

        card1.setColor1(new java.awt.Color(0, 100, 20));
        card1.setColor2(new java.awt.Color(152, 255, 150));
        jPanel1.add(card1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 90, 251, 212));

        card2.setColor1(new java.awt.Color(142, 142, 250));
        card2.setColor2(new java.awt.Color(167, 94, 236));
        jPanel1.add(card2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 251, 212));
        jPanel1.add(chart, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 840, 270));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/portada2.jpg"))); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 100, 250, 200));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.component.Card card1;
    private com.raven.component.Card card2;
    private com.raven.chart.Chart chart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
