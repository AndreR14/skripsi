package datamaster;

import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import koneksi.Koneksi;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class BarangKeluar extends javax.swing.JFrame {

    private final DefaultTableModel defaultTableModel;
    private Connection connection = Koneksi.getKoneksi();
    private int bay, bel, jum, hj, jj, kem, unt, tot, totbel;
    private String awal;

    public BarangKeluar() {
        initComponents();
        txtFaktur.setEnabled(false);
        satu.setEnabled(false);
        masuk.setEnabled(false);
        kembalian.setEnabled(false);
        keuntungan.setEnabled(false);
        total.setEnabled(false);
        defaultTableModel = new DefaultTableModel();
        tabelInput.setModel(defaultTableModel);
        defaultTableModel.addColumn("Faktur");
        defaultTableModel.addColumn("Nama Pelanggan");
        defaultTableModel.addColumn("Nama Barang");
        defaultTableModel.addColumn("Satuan Barang");
        defaultTableModel.addColumn("Harga Jual");
        defaultTableModel.addColumn("Jumlah Jual");
        defaultTableModel.addColumn("Harga Jual Total");
        defaultTableModel.addColumn("Untung");
        defaultTableModel.addColumn("Kembalian");
        defaultTableModel.addColumn("Tanggal");
        loadData();
        tampilKodePelanggan();
        tampilCetak();
    }

    private void FilterAngka(KeyEvent a) {
        if (Character.isAlphabetic(a.getKeyChar())) {
            a.consume();
            JOptionPane.showMessageDialog(null, "MASUKAN ANGKA SAJA", "Nayya Beauty Clinic", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void loadData() {
        try {
            save.setEnabled(true);
            defaultTableModel.getDataVector().removeAllElements();
            defaultTableModel.fireTableDataChanged();
            String sql = "SELECT * FROM penjualan";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            Statement statementRelation = connection.createStatement();
            ResultSet resultSetRelation;

            while (resultSet.next()) {
                Object[] objects = new Object[10];
                objects[0] = resultSet.getString("faktur");

                resultSetRelation = statementRelation.executeQuery("SELECT Nama FROM customer WHERE Id = '" + resultSet.getString("kodepelanggan") + "'");
                resultSetRelation.next();
                objects[1] = resultSetRelation.getString("Nama");

                resultSetRelation = statementRelation.executeQuery("SELECT nama_barang FROM barang WHERE id = '" + resultSet.getString("kodebarang") + "'");
                resultSetRelation.next();
                objects[2] = resultSetRelation.getString("nama_barang");

                resultSetRelation = statementRelation.executeQuery("SELECT jenisatuan FROM satuan WHERE kodesatuan = '" + resultSet.getString("kodesatuan") + "'");
                resultSetRelation.next();
                objects[3] = resultSetRelation.getString("jenisatuan");

                objects[4] = resultSet.getString("hargajual");
                objects[5] = resultSet.getString("jumlahjual");
                objects[6] = resultSet.getString("hargajualtotal");
                objects[7] = resultSet.getString("kembali");
                objects[8] = resultSet.getString("untung");
                objects[9] = resultSet.getString("tanggal");
                defaultTableModel.addRow(objects);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Diload " + e);
        }
    }

    private void tampilCetak() {
        try {
            String sql = "SELECT * FROM penjualan";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                daftar.addItem(resultSet.getString("faktur"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void tampilKodePelanggan() {
        try {
            String sql = "SELECT * FROM customer";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println(resultSet.getString("Id"));
                System.out.println(resultSet.getString("Nama"));
                cus.addItem(resultSet.getString("Id") + " " + resultSet.getString("Nama"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void tampilKodeBarang() {
        try {
            String sql1, sql2;
            sql1 = "SELECT * FROM pembelianbarang GROUP BY kodebarang";

            Statement st1 = connection.createStatement();
            Statement st2 = connection.createStatement();
            ResultSet rs1, rs2;
            rs1 = st1.executeQuery(sql1);
            int i = 0;
            while (rs1.next()) {
                sql2 = "SELECT * FROM barang WHERE id = '" + rs1.getString("kodebarang") + "'";
                System.out.println(sql2);
                rs2 = st2.executeQuery(sql2);
                rs2.next();
                bar.addItem(rs2.getString("id") + " " + rs2.getString("nama_barang"));
                i++;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void tampilAtributBarang() {
        try {
            int i = 1;
            String sql1 = "SELECT * FROM pembelianbarang WHERE kodebarang LIKE '"
                    + bar.getSelectedItem().toString().substring(0, 6)
                    + "' GROUP BY kodebarang";
            System.out.println("SQL 1 " + sql1);
            Statement st1 = connection.createStatement();
            ResultSet rs1;
            rs1 = st1.executeQuery(sql1);
            if (rs1.next()) {
//                rs1.absolute(i);
                String sql2 = "SELECT * FROM satuan WHERE kodesatuan = '" + rs1.getString("kodesatuan") + "'";
                System.out.println(sql2);
                Statement st2 = connection.createStatement();
                ResultSet rs2;
                rs2 = st2.executeQuery(sql2);
                if (rs2.next()) {
//                    rs2.absolute(i);
                    satu.setText(rs2.getString("kodesatuan") + " " + rs2.getString("jenisatuan"));
                    masuk.setText(rs1.getString("hargabeli"));
                }

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void kodeJual() {
        try {
            connection = Koneksi.getKoneksi();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM penjualan ORDER BY faktur DESC";
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                String kbar = resultSet.getString("faktur").substring(1);
                System.out.println(kbar);
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
                awal = "H" + Nol + AN;
            } else {
                awal = "H00001";
            }
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        txtFaktur.setText(awal);
    }

    private void relasiCetak() {
        String rcetak = ("%" + daftar.getSelectedItem().toString() + "%");
        String rbarang = ("%" + bar.getSelectedItem().toString().substring(0, 6) + "%");
        String rsatuan = ("%" + satu.getText().substring(0, 6) + "%");
        try {
            HashMap parameter = new HashMap();
            parameter.put("faktur", rcetak); // nilainya itu yang rcetak. rcetak di dapat dari
            parameter.put("id_barang", rbarang);
            parameter.put("kode_satuan", rsatuan);
            InputStream file = getClass().getResourceAsStream("/report/Report Faktur.jrxml");
            JasperDesign JasperDesign = JRXmlLoader.load(file);
            JasperReport JasperReport = JasperCompileManager.compileReport(JasperDesign);
            JasperPrint JasperPrint = JasperFillManager.fillReport(JasperReport, parameter, connection);
            JasperViewer.viewReport(JasperPrint, false);
        } catch (JRException e) {
            System.out.println(e);
        }
    }

    private void kosong() {
        keluar.setText("");
        jumlahJual.setText("");
        total.setText("");
        bayaran.setText("");
        kembalian.setText("");
        keuntungan.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtFaktur = new javax.swing.JTextField();
        satu = new javax.swing.JTextField();
        jumlahJual = new javax.swing.JTextField();
        keluar = new javax.swing.JTextField();
        cus = new javax.swing.JComboBox<>();
        masuk = new javax.swing.JTextField();
        total = new javax.swing.JTextField();
        bayaran = new javax.swing.JTextField();
        kembalian = new javax.swing.JTextField();
        keuntungan = new javax.swing.JTextField();
        hitung = new javax.swing.JButton();
        bayar = new javax.swing.JButton();
        save = new javax.swing.JButton();
        daftar = new javax.swing.JComboBox<>();
        cetak = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelInput = new javax.swing.JTable();
        bar = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("PENGELUARAN BARANG");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(297, 297, 297)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Faktur");

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Nama Barang");

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Satuan");

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Harga Pemasukkan");

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Harga Pengeluaran");

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Nama Pelanggan");

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Jumlah Jual");

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Total");

        jLabel10.setBackground(new java.awt.Color(0, 0, 0));
        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Keuntungan");

        jLabel11.setBackground(new java.awt.Color(0, 0, 0));
        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Pembayaran");

        jLabel12.setBackground(new java.awt.Color(0, 0, 0));
        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Kembalian");

        txtFaktur.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        satu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jumlahJual.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jumlahJual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jumlahJualKeyTyped(evt);
            }
        });

        keluar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        keluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                keluarKeyTyped(evt);
            }
        });

        cus.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cusActionPerformed(evt);
            }
        });

        masuk.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        total.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        bayaran.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        bayaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                bayaranKeyTyped(evt);
            }
        });

        kembalian.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        keuntungan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        hitung.setText("Hitung");
        hitung.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        hitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hitungActionPerformed(evt);
            }
        });

        bayar.setText("Bayar");
        bayar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bayarActionPerformed(evt);
            }
        });

        save.setText("Simpan");
        save.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        daftar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        cetak.setText("Cetak");
        cetak.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cetakActionPerformed(evt);
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

        bar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        bar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/icons8_back_arrow_20px.png"))); // NOI18N
        jButton1.setText("Kembali");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(hitung, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(keluar, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(satu, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(100, 100, 100)
                                        .addComponent(txtFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(45, 45, 45)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel10)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jumlahJual, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(masuk, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel3))
                                .addGap(29, 29, 29)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(bar, 0, 178, Short.MAX_VALUE))))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(bayaran, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(bayar))
                                .addComponent(total)
                                .addComponent(keuntungan))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(save, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(daftar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cetak, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)))
                            .addComponent(kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(satu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(bayaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bayar))
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel7)
                    .addComponent(cus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(keuntungan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(masuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(save, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(daftar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jumlahJual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cetak, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(keluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(hitung)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void hitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hitungActionPerformed
        hj = Integer.parseInt(keluar.getText());
        jj = Integer.parseInt(jumlahJual.getText());
        if (Integer.parseInt(masuk.getText()) > Integer.parseInt(keluar.getText())) {
            JOptionPane.showMessageDialog(null, "Harga pengeluaran barang lebih kecil dari pemasukan");

        } else {
            jum = hj * jj;
            String jumlahLocal = String.valueOf(jum);
            total.setText(jumlahLocal);
        }
    }//GEN-LAST:event_hitungActionPerformed

    private void bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bayarActionPerformed
        tot = Integer.parseInt(total.getText());
        bay = Integer.parseInt(bayaran.getText());
        hj = Integer.parseInt(keluar.getText());
        jj = Integer.parseInt(jumlahJual.getText());
        bel = Integer.parseInt(masuk.getText());
        kem = bay - tot;
        if (kem < 0) {
            JOptionPane.showMessageDialog(null, "Pembayaran tidak mencukupi total penjualan");
            JOptionPane.showMessageDialog(null, "Kurang Rp." + (Integer.parseInt((total.getText())) - Integer.parseInt(bayaran.getText())));
        } else {
            totbel = bel * jj;
            unt = tot - totbel;
            String kembali = String.valueOf(kem);
            kembalian.setText(kembali);
            String untung = String.valueOf(unt);
            keuntungan.setText(untung);
        }
    }//GEN-LAST:event_bayarActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        if (keluar.getText().equals("")
                || jumlahJual.getText().equals("")
                || bayaran.getText().equals("")
                || total.getText().equals("")
                || bayaran.getText().equals("")
                || keuntungan.getText().equals("")
                || kembalian.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "LENGKAPI DATA !", "Nayya Beauty Clinic", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String tfaktur = txtFaktur.getText();
            String tkpelanggan = cus.getSelectedItem().toString().substring(0, 6);
            String tkbarang = bar.getSelectedItem().toString().substring(0, 6);
            String tksatuan = satu.getText().substring(0, 6);
            String thjual = keluar.getText();
            String tjjual = jumlahJual.getText();
            String thjtotal = total.getText();
            String tbayar = bayaran.getText();
            String tkembali = kembalian.getText();
            String tuntung = keuntungan.getText();
            long millis = System.currentTimeMillis();
            java.sql.Date date = new java.sql.Date(millis);
            System.out.println(date);
            String ttanggal = date.toString();
            String sql = "INSERT INTO penjualan VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement pst = connection.prepareStatement(sql);
                pst.setString(1, tfaktur);
                pst.setString(2, tkpelanggan);
                pst.setString(3, tkbarang);
                pst.setString(4, tksatuan);
                pst.setString(5, thjual);
                pst.setString(6, tjjual);
                pst.setString(7, thjtotal);
                pst.setString(8, tbayar);
                pst.setString(9, tkembali);
                pst.setString(10, tuntung);
                pst.setString(11, ttanggal);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "DATA BERHASIL DISIMPAN", "Nayya Beauty Clinic", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }

            try {
                String sqlSelectStock = "SELECT kodestok, jumlahbarang FROM stok WHERE kodebarang = '" + tkbarang + "'";
                String jumlahBarang = "";
                String kodeStock = "";
                PreparedStatement preparedStatementSelectStock = connection.prepareStatement(sqlSelectStock);
              Statement statementKodeStockExist = connection.createStatement();
                ResultSet resultSetSelectStock = preparedStatementSelectStock.executeQuery(sqlSelectStock);
                while (resultSetSelectStock.next()) {
                    jumlahBarang = resultSetSelectStock.getString("jumlahbarang");
                    kodeStock = resultSetSelectStock.getString("kodestok");
                }
                int hasil = Integer.parseInt(jumlahBarang) - Integer.parseInt(tjjual);
                String sqlStock = "UPDATE stok SET "
                        + "jumlahbarang = '" + hasil + "' "
                        + "WHERE kodestok = '" + kodeStock + "'";
                PreparedStatement preparedStatementStock = connection.prepareStatement(sqlStock);
                preparedStatementStock.executeUpdate();
                JOptionPane.showMessageDialog(null, "STOCK BERHASIL DISIMPAN", "Nayya Beauty Clinic", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
            kosong();
            loadData();
            tampilCetak();
        }


    }//GEN-LAST:event_saveActionPerformed

    private void keluarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keluarKeyTyped
        FilterAngka(evt);
    }//GEN-LAST:event_keluarKeyTyped

    private void cusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cusActionPerformed

        if (cus.getItemCount() == 0) {
            tampilKodePelanggan();
        }

        tampilKodeBarang();
        bar.removeAllItems();
        if (bar.getItemCount() == 0) {
            tampilKodeBarang();

        }
        tampilAtributBarang();
        kodeJual();
    }//GEN-LAST:event_cusActionPerformed

    private void barActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barActionPerformed
     
        if (bar.getItemCount() == 0) {
            tampilKodeBarang();
        }
        tampilAtributBarang();
        kodeJual();
    }//GEN-LAST:event_barActionPerformed

    private void bayaranKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bayaranKeyTyped
        FilterAngka(evt);
    }//GEN-LAST:event_bayaranKeyTyped

    private void jumlahJualKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jumlahJualKeyTyped
        FilterAngka(evt);
    }//GEN-LAST:event_jumlahJualKeyTyped

    private void tabelInputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelInputMouseClicked
        save.setEnabled(false);

        int i = tabelInput.getSelectedRow();
        if (i == -1) {
            return;
        }

        String tableFaktur = (String) defaultTableModel.getValueAt(i, 0);
        txtFaktur.setText(tableFaktur);

        String tablePelanggan = (String) defaultTableModel.getValueAt(i, 1);
        cus.setSelectedItem(tablePelanggan);

        String tableBarang = (String) defaultTableModel.getValueAt(i, 2);
        bar.setSelectedItem(tableBarang);

        String tableSatuan = (String) defaultTableModel.getValueAt(i, 3);
        satu.setText(tableSatuan);

        String tableHargaJual = (String) defaultTableModel.getValueAt(i, 4);
        keluar.setText(tableHargaJual);

        String tableJumlahJual = (String) defaultTableModel.getValueAt(i, 5);
        jumlahJual.setText(tableJumlahJual);
    }//GEN-LAST:event_tabelInputMouseClicked

    private void cetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cetakActionPerformed
        relasiCetak();
    }//GEN-LAST:event_cetakActionPerformed

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
            java.util.logging.Logger.getLogger(BarangKeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BarangKeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BarangKeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BarangKeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BarangKeluar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> bar;
    private javax.swing.JButton bayar;
    private javax.swing.JTextField bayaran;
    private javax.swing.JButton cetak;
    private javax.swing.JComboBox<String> cus;
    private javax.swing.JComboBox<String> daftar;
    private javax.swing.JButton hitung;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jumlahJual;
    private javax.swing.JTextField keluar;
    private javax.swing.JTextField kembalian;
    private javax.swing.JTextField keuntungan;
    private javax.swing.JTextField masuk;
    private javax.swing.JTextField satu;
    private javax.swing.JButton save;
    private javax.swing.JTable tabelInput;
    private javax.swing.JTextField total;
    private javax.swing.JTextField txtFaktur;
    // End of variables declaration//GEN-END:variables

}
