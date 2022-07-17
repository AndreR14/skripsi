package datamaster;

import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.*;
import javax.swing.JFrame;
import koneksi.Koneksi;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DataBarang extends javax.swing.JFrame {

    private Connection conn = Koneksi.getKoneksi();
    private ResultSet rs;
    private PreparedStatement pst;
    private Statement st;
    private String sql;
    private final DefaultTableModel model;

    public DataBarang() {
      
        initComponents();
        idBarang.setEnabled(false);
        model = new DefaultTableModel();
        tabelInput.setModel(model);
        model.addColumn("ID");
        model.addColumn("Nama Barang");
        loadData();
        idbarang();
       
    }
//    private void kosong (){
//        idBarang.setText("");
//        namaBarang.setText("");
//    }
    private void idbarang() {
        try {
            conn = Koneksi.getKoneksi();
            st = conn.createStatement();

            sql = "SELECT * FROM barang ORDER by id DESC";
            rs = st.executeQuery(sql);

            if (rs.next()) {
                String ibar = rs.getString("id").substring(1);
                System.out.println(ibar);
                String AN = "" + (Integer.parseInt(ibar) + 1);
                System.out.println(AN);
                String Nol = "";

                if (AN.length() == 1) {
                    Nol = "0000";
                } else if (AN.length() == 2) {
                    Nol = "000";
                } else if (AN.length() == 3) {
                    Nol = "00";
                } else if (AN.length() == 4) {
                    Nol = "0";
                } else if (AN.length() == 5) {
                    Nol = "";
                }
                idBarang.setText("E" + Nol + AN);
            } else {
                idBarang.setText("E00001");
            }
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    private void FilterHuruf(KeyEvent a) {
        if (Character.isDigit(a.getKeyChar())) {
            a.consume();
            JOptionPane.showMessageDialog(null, "MASUKAN HURUF SAJA !", "NAYYA BEAUTY CLINIC", JOptionPane.WARNING_MESSAGE);
        }
    }
    private void FilterAngka(KeyEvent a) {
        if (Character.isAlphabetic(a.getKeyChar())) {
            a.consume();
            JOptionPane.showMessageDialog(null, "MASUKAN ANGKA SAJA", "NAYYA BEAUTY CLINIC", JOptionPane.WARNING_MESSAGE);
        }
    }
    public final void loadData() {
        simpanBarang.setEnabled(true);
        hapusBarang.setEnabled(false);
        editBarang.setEnabled(false);
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            conn = Koneksi.getKoneksi();
            st = conn.createStatement();

            sql = "SELECT * FROM barang";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Object[] o = new Object[2];
                o[0] = rs.getString("id");
                o[1] = rs.getString("nama_barang");

                model.addRow(o);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan " + e);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        idBarang = new javax.swing.JTextField();
        namaBarang = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        simpanBarang = new javax.swing.JButton();
        hapusBarang = new javax.swing.JButton();
        editBarang = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelInput = new javax.swing.JTable();
        kembali = new javax.swing.JButton();
        cari = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Microsoft Himalaya", 1, 24)); // NOI18N
        jLabel1.setText("DATA BARANG CV ADHI RAHAYU");

        jLabel2.setFont(new java.awt.Font("Microsoft Himalaya", 1, 14)); // NOI18N
        jLabel2.setText("ID BARANG");

        jLabel3.setFont(new java.awt.Font("Microsoft Himalaya", 1, 14)); // NOI18N
        jLabel3.setText("INPUT BARANG");

        jLabel4.setFont(new java.awt.Font("Microsoft Himalaya", 1, 14)); // NOI18N
        jLabel4.setText("NAMA BARANG");

        idBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idBarangActionPerformed(evt);
            }
        });
        idBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                idBarangKeyTyped(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        simpanBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_save_20px.png"))); // NOI18N
        simpanBarang.setText("Simpan");
        simpanBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanBarangActionPerformed(evt);
            }
        });

        hapusBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_trash_20px.png"))); // NOI18N
        hapusBarang.setText("Hapus");
        hapusBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusBarangActionPerformed(evt);
            }
        });

        editBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_pencil_20px.png"))); // NOI18N
        editBarang.setText("Edit");
        editBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBarangActionPerformed(evt);
            }
        });

        tabelInput.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelInput.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelInputMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelInput);

        kembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_back_arrow_20px.png"))); // NOI18N
        kembali.setText("Kembali");
        kembali.setBorder(null);
        kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kembaliActionPerformed(evt);
            }
        });

        cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariActionPerformed(evt);
            }
        });
        cari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cariKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_search_20px.png"))); // NOI18N
        jLabel5.setText("Cari");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 748, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 748, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 748, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(idBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(32, 32, 32))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cari, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(namaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(hapusBarang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(editBarang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(simpanBarang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(kembali, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(261, 261, 261)
                        .addComponent(jLabel3)))
                .addContainerGap(33, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(182, 182, 182))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(idBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(simpanBarang)
                    .addComponent(hapusBarang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(namaBarang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(editBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void simpanBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanBarangActionPerformed
        if (namaBarang.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "LENGKAPI DATA !", "Nayya Beauty Clinic", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String tidbarang = idBarang.getText();
            String tnamabarang = namaBarang.getText();

            try {
                conn = Koneksi.getKoneksi();
                sql = "INSERT INTO barang VALUES (?, ?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, tidbarang);
                pst.setString(2, tnamabarang);
                pst.executeUpdate();
                pst.close();
                JOptionPane.showMessageDialog(null, "DATA BERHASIL DISIMPAN", "PT MULIA JAYA TEXTILE", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                loadData();
                idbarang();
                namaBarang.setText("");
            }
        }

    }//GEN-LAST:event_simpanBarangActionPerformed

    private void idBarangKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idBarangKeyTyped
    //FilterAngka(evt);
    }//GEN-LAST:event_idBarangKeyTyped

    private void hapusBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusBarangActionPerformed
        // TODO add your handling code here:
        try {
            sql = "DELETE FROM barang WHERE nama_barang='" + namaBarang.getText() + "'";
            conn = (Connection) Koneksi.getKoneksi();
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "DATA BERHASIL DIHAPUS", "PT MULIA JAYA TEXTILE", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Data Gagal di Hapus");
        }
        loadData();
        idbarang();
        namaBarang.setText("");
    }//GEN-LAST:event_hapusBarangActionPerformed

    private void idBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idBarangActionPerformed

    private void tabelInputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelInputMouseClicked
        // TODO add your handling code here:
        simpanBarang.setEnabled(false);
        hapusBarang.setEnabled(true);
        editBarang.setEnabled(true);
        int i = tabelInput.getSelectedRow();
        if (i == -1) {
            return;
        }
        String tablekodebarang = (String) model.getValueAt(i, 0);
        idBarang.setText(tablekodebarang);
        idBarang.setEnabled(false);

        String tablenamabarang = (String) model.getValueAt(i, 1);
        namaBarang.setText(tablenamabarang);

    }//GEN-LAST:event_tabelInputMouseClicked

    private void kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kembaliActionPerformed
        // TODO add your handling code here:
        Awal lg = new Awal();
        lg.setVisible(true);
        lg.pack();
        lg.setLocationRelativeTo(null);
        lg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_kembaliActionPerformed

    private void editBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBarangActionPerformed
        // TODO add your handling code here:
        if (namaBarang.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "LENGKAPI DATA !", "NAYYA BEAUTY CLINIC", JOptionPane.INFORMATION_MESSAGE);
        } else {
            int i = tabelInput.getSelectedRow();
            if (i == -1) {
                return;
            }
            try {
                Connection c = Koneksi.getKoneksi();
                sql = "UPDATE barang SET nama_barang=? WHERE id='" + idBarang.getText() + "'";
                PreparedStatement p = c.prepareStatement(sql);
                p.setString(1, namaBarang.getText());
                p.executeUpdate();
                idBarang.requestFocus();
                JOptionPane.showMessageDialog(null, "DATA BERHASIL DIEDIT", "NAYYA BEAUTY CLINIC", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            } finally {
                loadData();
                idbarang();
                namaBarang.setText("");
            }
        }
    }//GEN-LAST:event_editBarangActionPerformed

    private void cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cariActionPerformed

    private void cariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cariKeyReleased
        // TODO add your handling code here:
         model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            conn = Koneksi.getKoneksi();
            st = conn.createStatement();

            sql = "SELECT * FROM barang WHERE "
                    + "id LIKE '%" + cari.getText()
                    + "%' OR nama_barang LIKE'%" + cari.getText() + "%'";
            rs = st.executeQuery(sql);

            while (rs.next()) {
                Object[] o = new Object[2];
                o[0] = rs.getString("id");
                o[1] = rs.getString("nama_barang");

                model.addRow(o);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_cariKeyReleased

       public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> {
            new DataBarang().setVisible(true);
        });
       }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField cari;
    private javax.swing.JButton editBarang;
    private javax.swing.JButton hapusBarang;
    private javax.swing.JTextField idBarang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton kembali;
    private javax.swing.JTextField namaBarang;
    private javax.swing.JButton simpanBarang;
    private javax.swing.JTable tabelInput;
    // End of variables declaration//GEN-END:variables

}
