
package datamaster;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.*;
import javax.swing.JFrame;
import koneksi.Koneksi;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class BarangMasuk extends javax.swing.JFrame {
 private final DefaultTableModel defaultTableModel;
    private Connection connection = Koneksi.getKoneksi();
    private double tot, hb, jumlahHitung;
    private String stock;
    public BarangMasuk() {
        initComponents();
        kodePem.setEnabled(false);
        jum.setEnabled(false);
//        bsimpan.setEnabled(false);
        defaultTableModel = new DefaultTableModel();
        tabelInput.setModel(defaultTableModel);
        defaultTableModel.addColumn("Kode Beli");
        defaultTableModel.addColumn("Kode Stock");
        defaultTableModel.addColumn("Nama Supplier");
        defaultTableModel.addColumn("Kategori Barang");
        defaultTableModel.addColumn("Nama Barang");
        defaultTableModel.addColumn("Satuan Barang");
        defaultTableModel.addColumn("Jumlah Barang");
        defaultTableModel.addColumn("Harga Beli");
        defaultTableModel.addColumn("Total");
        defaultTableModel.addColumn("Tanggal");
        loadData();
        tampilSupplier();
        tampilKategori();
        tampilBarang();
        tampilSatuan();
        kodeBeli();
        kodeStock();
        kosong();
    }
     private void existBarang() {
        String sql = "SELECT kodebarang, kodekat, kodesatuan FROM pembelianbarang WHERE kodebarang = '" + namaBar.getSelectedItem().toString().substring(0, 6) + "' GROUP BY kodebarang";
        System.out.println(sql);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            resultSet.next();
            if (resultSet.getString("kodekat").equals(kat.getSelectedItem().toString().subSequence(0, 6)) || resultSet.getString("kodesatuan").equals(comsat.getSelectedItem().toString().subSequence(0, 6))) {
                JOptionPane.showMessageDialog(null, "Data barang dengan kode barang " + namaBar.getSelectedItem().toString().substring(0, 6) + " mempunyai satuan yang berbeda", "Nayya Beauty Clinic", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan" + e);
        }
    }
     private void FilterHuruf(KeyEvent a) {
        if (Character.isDigit(a.getKeyChar())) {
            a.consume();
            JOptionPane.showMessageDialog(null, "MASUKAN HURUF SAJA !", "Nayya Beauty Clinic", JOptionPane.WARNING_MESSAGE);
        }
    }
     private void FilterAngka(KeyEvent a) {
        if (Character.isAlphabetic(a.getKeyChar())) {
            a.consume();
            JOptionPane.showMessageDialog(null, "MASUKAN ANGKA SAJA", "Nayya Beauty Clinic", JOptionPane.WARNING_MESSAGE);
        }
    }
     private void loadData() {
        hapus.setEnabled(false);
//        bedit.setEnabled(false);
        defaultTableModel.getDataVector().removeAllElements();
        defaultTableModel.fireTableDataChanged();
        try {
            String sql = "SELECT * FROM pembelianbarang";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            Statement statementRelation = connection.createStatement();
            ResultSet resultSetRelation;

            Object[] objects = new Object[10];
            while (resultSet.next()) {

                objects[0] = resultSet.getString("kodebeli");

                objects[1] = resultSet.getString("kodestok");

                resultSetRelation = statementRelation.executeQuery("SELECT Nama_supplier FROM supplier WHERE Kode_supplier = '" + resultSet.getString("kodesupplier") + "'");
                resultSetRelation.next();
                objects[2] = resultSetRelation.getString("Nama_supplier");

                resultSetRelation = statementRelation.executeQuery("SELECT nama_kategori FROM kategori WHERE kode_kategori = '" + resultSet.getString("kodekat") + "'");
                resultSetRelation.next();
                objects[3] = resultSetRelation.getString("nama_kategori");

                resultSetRelation = statementRelation.executeQuery("SELECT nama_barang FROM barang WHERE id = '" + resultSet.getString("kodebarang") + "'");
                resultSetRelation.next();
                objects[4] = resultSetRelation.getString("nama_barang");

                resultSetRelation = statementRelation.executeQuery("SELECT jenisatuan FROM satuan WHERE kodesatuan = '" + resultSet.getString("kodesatuan") + "'");
                resultSetRelation.next();
                objects[5] = resultSetRelation.getString("jenisatuan");

                objects[6] = resultSet.getString("jumlahbarang");
                objects[7] = resultSet.getString("hargabeli");
                objects[8] = resultSet.getString("total");
                objects[9] = resultSet.getString("tanggal");
                defaultTableModel.addRow(objects);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Di Load" + e);
        }
    }
     
     private void tampilSupplier() {
        try {
            String sql = "SELECT * FROM supplier ORDER by Kode_supplier";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                sup.addItem(resultSet.getString("Kode_supplier") + " " + resultSet.getString("Nama_supplier"));
            }
            
            

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
     
     private void tampilKategori() {
        try {
            String sql = "SELECT * FROM kategori ORDER by kode_kategori";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                kat.addItem(resultSet.getString(1) + " " + resultSet.getString(2));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
     private void tampilBarang() {
        try {
            String sql = "SELECT * FROM barang ORDER by id";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                namaBar.addItem(resultSet.getString(1) + " " + resultSet.getString(2));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
     
     private void tampilSatuan() {
        try {
            String sql = "SELECT * FROM satuan ORDER by kodesatuan";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                comsat.addItem(resultSet.getString(1) + " " + resultSet.getString(2));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
     
     private void kodeBeli() {
        try {
            connection = Koneksi.getKoneksi();
            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM pembelianbarang ORDER by kodebeli DESC";
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                String kbar = resultSet.getString("kodebeli").substring(1);
                String AN = "" + (Integer.parseInt(kbar) + 1);
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
                kbar = "F" + Nol + AN;
                try {
                    kodePem.setText(kbar);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            } else {
                String kbar = "F00001";
                try {
                    kodePem.setText(kbar);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        } catch (HeadlessException | NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
     
     private void kodeStock() {
        try {
            connection = Koneksi.getKoneksi();
            String sqlKodeStock = "SELECT * FROM pembelianbarang ORDER by kodestok DESC";
            Statement statement = connection.createStatement();
            ResultSet resultSetKodeStock = statement.executeQuery(sqlKodeStock);

            if (resultSetKodeStock.next()) {
                String kbar = resultSetKodeStock.getString("kodestok").substring(1);
                String AN = "" + (Integer.parseInt(kbar) + 1);
                String Nol = "";
                switch (AN.length()) {
                    case 1:
                        Nol = "0000";
                        break;
                    case 2:
                        Nol = "000";
                        break;
                    case 3:
                        Nol = "00";
                        break;
                    case 4:
                        Nol = "0";
                        break;
                    case 5:
                        Nol = "";
                        break;
                    default:
                        break;
                }
                stock = "G" + Nol + AN;
            } else {
                stock = "G00001";
            }

            String sqlExist = "SELECT * FROM pembelianbarang WHERE "
                    + "kodekat = '" + kat.getSelectedItem().toString().substring(0, 6) + "' AND "
                    + "kodebarang = '" + namaBar.getSelectedItem().toString().substring(0, 6) + "' AND "
                    + "kodesatuan = '" + comsat.getSelectedItem().toString().substring(0, 6) + "' ";
            System.out.println(sqlExist);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlExist);
            ResultSet resultSet = preparedStatement.executeQuery(sqlExist);
            String sama1 = "", sama2 = "", sama3 = "", sama4 = "";
            while (resultSet.next()) {
                sama1 = resultSet.getString("kodekat");
                sama2 = resultSet.getString("kodebarang");
                sama3 = resultSet.getString("kodesatuan");
                sama4 = resultSet.getString("kodestok");
            }
            preparedStatement.close();
            resultSet.close();

            if (kat.getSelectedItem().toString().substring(0, 6).equals(sama1)
                    && namaBar.getSelectedItem().toString().substring(0, 6).equals(sama2)
                    && comsat.getSelectedItem().toString().substring(0, 6).equals(sama3)) {
                stock = sama4;
            }

        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
     private void kosong() {
        sat.setText("");
        hargaPem.setText("");
        jum.setText("");
    }


   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        kodePem = new javax.swing.JTextField();
        kat = new javax.swing.JComboBox<>();
        comsat = new javax.swing.JComboBox<>();
        sat = new javax.swing.JTextField();
        hargaPem = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        sup = new javax.swing.JComboBox<>();
        namaBar = new javax.swing.JComboBox<>();
        hitung = new javax.swing.JButton();
        jum = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        simpan = new javax.swing.JToggleButton();
        hapus = new javax.swing.JToggleButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelInput = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("PEMASUKKAN BARANG");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(361, 361, 361)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Kode Pemasukkan");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Kategori");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Satuan");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Harga Pemasukkan");

        kat.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                katItemStateChanged(evt);
            }
        });

        comsat.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comsatItemStateChanged(evt);
            }
        });

        sat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                satActionPerformed(evt);
            }
        });
        sat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                satKeyPressed(evt);
            }
        });

        hargaPem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                hargaPemKeyPressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Supplier");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Nama Barang");

        sup.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                supItemStateChanged(evt);
            }
        });
        sup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supActionPerformed(evt);
            }
        });

        namaBar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                namaBarItemStateChanged(evt);
            }
        });

        hitung.setText("Hitung");
        hitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hitungActionPerformed(evt);
            }
        });

        jum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jumKeyPressed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Rp.");

        simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_save_20px.png"))); // NOI18N
        simpan.setText("SIMPAN");
        simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanActionPerformed(evt);
            }
        });

        hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_trash_20px.png"))); // NOI18N
        hapus.setText("HAPUS");
        hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusActionPerformed(evt);
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
        jScrollPane1.setViewportView(tabelInput);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Rp.");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_back_arrow_20px.png"))); // NOI18N
        jButton1.setText("Kembali");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(hargaPem, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(sat, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(comsat, 0, 138, Short.MAX_VALUE))
                                        .addComponent(kodePem, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(kat, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(41, 41, 41)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel8)
                                                .addGap(19, 19, 19))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jum)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(hitung)
                                                .addGap(0, 249, Short.MAX_VALUE))
                                            .addComponent(namaBar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                                        .addComponent(sup, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(771, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(kodePem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(sup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(kat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(namaBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(comsat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(hitung)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(jLabel9)
                        .addComponent(hargaPem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)))
                .addGap(18, 18, 18)
                .addComponent(simpan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hapus)
                .addGap(1, 1, 1)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(113, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void satActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_satActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_satActionPerformed

    private void simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanActionPerformed
        kodeStock();
        String tkodebeli = kodePem.getText();
        String tkodestock = stock;
        String tsupplier = sup.getSelectedItem().toString().substring(0, 6);
        String tkategori = kat.getSelectedItem().toString().substring(0, 6);
        String tkodebarang = namaBar.getSelectedItem().toString().substring(0, 6);
        String tsatuan = comsat.getSelectedItem().toString().substring(0, 6);
        String tjumlah = sat.getText();
        String thargabeli = hargaPem.getText();
        String ttotal = jum.getText();

        try {
//            String sql = "SELECT kodebarang, kodekategori, kodesatuan FROM belibarang WHERE kodebarang = '" + ckbarang.getSelectedItem().toString().substring(0, 6) + "' GROUP BY kodebarang";
//            System.out.println(sql);
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            if (preparedStatement.executeQuery().first()) {
//                ResultSet resultSet = preparedStatement.executeQuery(sql);
//                resultSet.next();
//                if (!resultSet.getString("kodekategori").equals(tkategori) || !resultSet.getString("kodesatuan").equals(tsatuan)) {
//                    JOptionPane.showMessageDialog(null, "Data barang dengan kode barang " + ckbarang.getSelectedItem().toString().substring(0, 6) + " mempunyai satuan yang berbeda", "PT MULIA JAYA TEXTILE", JOptionPane.INFORMATION_MESSAGE);
//                }
//            }
            
            if (sat.getText().equals("")
                    || hargaPem.getText().equals("")
                    || jum.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "LENGKAPI DATA !", "Nayya Beauty Clinic", JOptionPane.INFORMATION_MESSAGE);
            } else {
                long millis = System.currentTimeMillis();
                java.sql.Date date = new java.sql.Date(millis);
                String ttanggal = date.toString();
                String sqlInsetPembelianBarang = "INSERT INTO pembelianbarang VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlInsetPembelianBarang);
                preparedStatement.setString(1, tkodebeli);
                preparedStatement.setString(2, tkodestock);
                preparedStatement.setString(3, tsupplier);
                preparedStatement.setString(4, tkategori);
                preparedStatement.setString(5, tkodebarang);
                preparedStatement.setString(6, tsatuan);
                preparedStatement.setString(7, tjumlah);
                preparedStatement.setString(8, thargabeli);
                preparedStatement.setString(9, ttotal);
                preparedStatement.setString(10, ttanggal);
                System.out.println(preparedStatement.toString());
                preparedStatement.executeUpdate();
                preparedStatement.close();

                String sql = "SELECT b.kodesatuan "
                        + "FROM stok a "
                        + "LEFT JOIN pembelianbarang b ON a.kodestok = b.kodestok "
                        + "WHERE b.kodestok = '" + tkodestock + "' "
                        + "AND b.kodekat = '" + tkategori + "' "
                        + "AND b.kodebarang = '" + tkodebarang + "' "
                        + "AND b.kodesatuan = '" + tsatuan + "' GROUP BY b.kodesatuan";
                preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    preparedStatement = connection.prepareStatement(
                            "INSERT INTO stok VALUES ("
                            + "'" + stock + "', "
                            + "'" + tkategori + "', "
                            + "'" + tkodebarang + "', "
                            + "'" + tsatuan + "', "
                            + "'" + tjumlah + "')");
                    System.out.println("INSERT INTO stok VALUES ("
                            + "'" + stock + "', "
                            + "'" + tkategori + "', "
                            + "'" + tkodebarang + "', "
                            + "'" + tsatuan + "', "
                            + "'" + tjumlah + "')");
                    preparedStatement.executeUpdate();
                } else {
                    System.out.println("UPDATE stok SET "
                            + "jumlahbarang = jumlahbarang + " + tjumlah + " "
                            + "WHERE kodestok = '" + stock + "'");
                    preparedStatement = connection.prepareStatement(
                            "UPDATE stok SET "
                            + "jumlahbarang = jumlahbarang + '" + tjumlah + "' "
                            + "WHERE kodestok = '" + stock + "'"
                    );
//                    System.out.println("UPDATE stok SET "
//                            + "jumlahbarang = jumlahbarang + " + tjumlah + " "
//                            + "WHERE kodestok = '" + stock + "'");
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data gagal disimpan pada tabel Pembelian barang " + e);
        }

        loadData();
        kodeBeli();
        kosong();
    }//GEN-LAST:event_simpanActionPerformed

    private void tabelInputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelInputMouseClicked
         simpan.setEnabled(false);
//        bedit.setEnabled(true);
        hapus.setEnabled(true);
//        ckbarang.setEnabled(false);
//        ckkategori.setEnabled(false);
//        cksatuan.setEnabled(false);
//        cksupplier.setEnabled(false);

        int i = tabelInput.getSelectedRow();
        if (i == -1) {
            return;
        }

        String tablekodebeli = (String) defaultTableModel.getValueAt(i, 0);
        kodePem.setText(tablekodebeli);

        String tablekodestock = (String) defaultTableModel.getValueAt(i, 1);
        stock = tablekodestock;

        String tablesupplier = (String) defaultTableModel.getValueAt(i, 2);
        sup.setSelectedItem(tablesupplier);

        String tablekategori = (String) defaultTableModel.getValueAt(i, 3);
        kat.setSelectedItem(tablekategori);

        String tablenamabarang = (String) defaultTableModel.getValueAt(i, 4);
        comsat.setSelectedItem(tablenamabarang);

        String tablesatuan = (String) defaultTableModel.getValueAt(i, 5);
        kat.setSelectedItem(tablesatuan);
    }//GEN-LAST:event_tabelInputMouseClicked

    private void hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusActionPerformed
        kodeStock();
        try {
            String sql = "DELETE FROM pembelianbarang WHERE kodebeli='" + kodePem.getText() + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        try {
            String sqlKodeStock = "SELECT kodestok, kodekat, kodesatuan, kodebarang, SUM(jumlahbarang) FROM pembelianbarang GROUP by kodestok, kodekat, kodesatuan, kodebarang";
            Statement statementKodeStock = connection.createStatement();
            ResultSet resultSetKodeStock = statementKodeStock.executeQuery(sqlKodeStock);

            while (resultSetKodeStock.next()) {
                String kodeStock = resultSetKodeStock.getString("kodestok");
                String kodeKategori = resultSetKodeStock.getString("kodekat");
                String kodeSatuan = resultSetKodeStock.getString("kodesatuan");
                String kodeBarang = resultSetKodeStock.getString("kodebarang");
                String jumlahBarang = resultSetKodeStock.getString("SUM(jumlahbarang)");
                PreparedStatement preparedStatement;
                System.out.println(kodeStock + " " + kodeKategori + " " + kodeSatuan + " " + kodeBarang + " " + jumlahBarang);
                System.out.println(stock);

                String sqlKodeStockExist = "SELECT kodestok FROM stok WHERE kodestok = '" + kodeStock + "'";
                Statement statementKodeStockExist = connection.createStatement();
                ResultSet resultKodeStockExist = statementKodeStockExist.executeQuery(sqlKodeStockExist);
                if (resultKodeStockExist.next() == false) {
                    preparedStatement = connection.prepareStatement(
                            "INSERT INTO stok VALUES ("
                            + "'" + stock + "', "
                            + "'" + kodeKategori + "', "
                            + "'" + kodeSatuan + "', "
                            + "'" + kodeBarang + "', "
                            + "'" + jumlahBarang + "')");
                    preparedStatement.executeUpdate();
                } else {
                    preparedStatement = connection.prepareStatement(
                            "UPDATE stok SET "
                            + "kodekat = '" + kodeBarang + "', "
                            + "kodesatuan = '" + kodeKategori + "', "
                            + "kodebarang = '" + kodeBarang + "', "
                            + "jumlahbarang = '" + jumlahBarang + "' "
                            + "WHERE kodestok = '" + kodeStock + "'"
                    );
                    preparedStatement.executeUpdate();
                }
                simpan.setEnabled(true);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data gagal disimpan pada tabel beli barang " + e);
        }
        loadData();
        kodeBeli();
        kosong();
    }//GEN-LAST:event_hapusActionPerformed

    private void comsatItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comsatItemStateChanged
        kodeBeli();
    }//GEN-LAST:event_comsatItemStateChanged

    private void hitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hitungActionPerformed
         hb = Integer.parseInt(hargaPem.getText());
        jumlahHitung = Integer.parseInt(sat.getText());
        tot = hb * jumlahHitung;
        String total = String.valueOf(tot);
        jum.setText(total);
    }//GEN-LAST:event_hitungActionPerformed

    private void katItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_katItemStateChanged
        kodeBeli();
    }//GEN-LAST:event_katItemStateChanged

    private void namaBarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_namaBarItemStateChanged
       kodeBeli();
    }//GEN-LAST:event_namaBarItemStateChanged

    private void supItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_supItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_supItemStateChanged

    private void supActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supActionPerformed
        kodeBeli();
    }//GEN-LAST:event_supActionPerformed

    private void satKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_satKeyPressed
        FilterAngka(evt);
    }//GEN-LAST:event_satKeyPressed

    private void hargaPemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hargaPemKeyPressed
        FilterAngka(evt);
    }//GEN-LAST:event_hargaPemKeyPressed

    private void jumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jumKeyPressed
        FilterAngka(evt);
    }//GEN-LAST:event_jumKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         Awal lg = new Awal();
        lg.setVisible(true);
        lg.pack();
        lg.setLocationRelativeTo(null);
        lg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BarangMasuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BarangMasuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BarangMasuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BarangMasuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BarangMasuk().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comsat;
    private javax.swing.JToggleButton hapus;
    private javax.swing.JTextField hargaPem;
    private javax.swing.JButton hitung;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jum;
    private javax.swing.JComboBox<String> kat;
    private javax.swing.JTextField kodePem;
    private javax.swing.JComboBox<String> namaBar;
    private javax.swing.JTextField sat;
    private javax.swing.JToggleButton simpan;
    private javax.swing.JComboBox<String> sup;
    private javax.swing.JTable tabelInput;
    // End of variables declaration//GEN-END:variables
}