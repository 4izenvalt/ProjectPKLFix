/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import com.sun.glass.events.KeyEvent;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import UI.Pembelian_KotakHistoriBarang;

/**
 *
 * @author User
 */
public final class Pembelian_Return extends javax.swing.JFrame {

    public String totalclone, kov;
    public Float Tempharga;
    public float subtotal1 = 0, hargajadi1 = 0, diskonke21 = 0, totalqty = 0, total = 0, diskon = 0, diskonp1 = 0, diskonp11, diskonp21 = 0; //penjumlahan
    public float jumlah = 0, harga = 0, subtotal = 0, diskonp = 0, diskonrp = 0, diskonp2 = 0, diskonrp2 = 0, hargajadi = 0;//panggil colom tabel
    public float qty = 0;
    public int i = 0, kode_barang = 0, nmtotal = 0;
    public TableModel tabelModel;

    public Pembelian_Return() {
        initComponents();
        this.setVisible(true);
        AutoCompleteDecorator.decorate(comFakturBeli);
        loadComboJenis();
        tanggal_jam_sekarang();
        AturlebarKolom();

    }

    public void loadComFakturBeli() {
        try {
            String sql = "SELECT * FROM `pembelian` ORDER BY `no_faktur_pembelian` ASC";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                Object[] order = new Object[3];
                order[0] = res.getString(3);
                comFakturBeli.addItem((String) order[0]);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void ClearTable() {

        DefaultTableModel model = (DefaultTableModel) tbl_Pembelian.getModel();
        int row = tbl_Pembelian.getRowCount();
        for (int i = 0; i < row; i++) {
            model.removeRow(0);
        }
    }

    void loadComboJenis() {
//        System.out.println(comJenis.getSelectedItem());
        if (comJenis.getSelectedItem().equals("BY FAKTUR BELI")) {
            autonumber();
            loadComFakturBeli();
            txt_Supplier.setEnabled(false);
            txt_namaSupply.setEnabled(false);
            txt_namaSupply.setText("");
            txt_almtSupply.setEnabled(false);
            txt_almtSupply.setText("");
            comFakturBeli.setEnabled(true);
        } else if (comJenis.getSelectedItem().equals("FAKTUR BEBAS")) {
            comFakturBeli.removeAllItems();
            comFakturBeli.setEnabled(false);
            txt_tanggal.setText("");
            txt_noRetur.setText("");
            txt_namaSupply.setEnabled(true);
            txt_almtSupply.setEnabled(true);

        }
    }

    public void tanggal_jam_sekarang() {
        Thread p = new Thread() {
            public void run() {
                for (;;) {
                    GregorianCalendar cal = new GregorianCalendar();
                    int hari = cal.get(Calendar.DAY_OF_MONTH);
                    int bulan = cal.get(Calendar.MONTH);
                    int tahun = cal.get(Calendar.YEAR);
                    int jam = cal.get(Calendar.HOUR_OF_DAY);
                    int menit = cal.get(Calendar.MINUTE);
                    int detik = cal.get(Calendar.SECOND);
                    txt_tanggal.setText(tahun + " - " + (bulan + 1) + " - " + hari + " " + jam + ":" + menit);

                }
            }
        };
        p.start();
    }

    void loadNumberTable() {
        int baris = tbl_Pembelian.getRowCount();
        for (int a = 0; a < baris; a++) {
            String nomor = String.valueOf(a + 1);
            tbl_Pembelian.setValueAt(nomor + ".", a, 0);
        }

    }

    public void autonumber() {
        try {
            String sql = "select max(no_faktur_penjualan) from penjualan_detail_return ORDER BY no_faktur_penjualan DESC";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                if (res.first() == false) {
                    txt_noRetur.setText("PB18-");

                } else {
                    res.last();
                    String auto_num = res.getString(1);
                    String no = String.valueOf(auto_num);
                    //  int noLong = no.length();
                    //MENGATUR jumlah 0
                    String huruf = String.valueOf(auto_num.substring(1, 5));
                    int angka = Integer.valueOf(auto_num.substring(5)) + 1;
                    txt_noRetur.setText(String.valueOf(huruf + "" + angka));

                }
            }
            res.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR: \n" + ex.toString(),
                    "Kesalahan", JOptionPane.WARNING_MESSAGE);
        }
    }

    void AturlebarKolom() {
        TableColumn column;
        tbl_Pembelian.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tbl_Pembelian.getColumnModel().getColumn(0);
        column.setPreferredWidth(30);
        column = tbl_Pembelian.getColumnModel().getColumn(1);
        column.setPreferredWidth(60);
        column = tbl_Pembelian.getColumnModel().getColumn(2);
        column.setPreferredWidth(200);
        column = tbl_Pembelian.getColumnModel().getColumn(3);
        column.setPreferredWidth(70);
        column = tbl_Pembelian.getColumnModel().getColumn(4);
        column.setPreferredWidth(60);
        column = tbl_Pembelian.getColumnModel().getColumn(5);
        column.setPreferredWidth(50);
        column = tbl_Pembelian.getColumnModel().getColumn(6);
        column.setPreferredWidth(100);
        column = tbl_Pembelian.getColumnModel().getColumn(7);
        column.setPreferredWidth(100);
        column = tbl_Pembelian.getColumnModel().getColumn(8);
        column.setPreferredWidth(60);
        column = tbl_Pembelian.getColumnModel().getColumn(9);
        column.setPreferredWidth(60);
        column = tbl_Pembelian.getColumnModel().getColumn(10);
        column.setPreferredWidth(60);
        column = tbl_Pembelian.getColumnModel().getColumn(11);
        column.setPreferredWidth(80);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comTableBarang = new javax.swing.JComboBox<>();
        comTableKonv = new javax.swing.JComboBox<>();
        comTableLokasi = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        txt_noRetur = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel32 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel69 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel70 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel71 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        txt_namaSupply = new javax.swing.JTextField();
        txt_almtSupply = new javax.swing.JTextField();
        txt_Keterangan = new javax.swing.JTextField();
        comJenis = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_Pembelian = new javax.swing.JTable();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txt_diskonPersen = new javax.swing.JTextField();
        txt_diskonRp = new javax.swing.JTextField();
        comFakturBeli = new javax.swing.JComboBox<>();
        txt_tanggal = new javax.swing.JTextField();
        txt_Supplier = new javax.swing.JTextField();

        comTableBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comTableBarangActionPerformed(evt);
            }
        });

        comTableKonv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comTableKonvActionPerformed(evt);
            }
        });

        comTableLokasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comTableLokasiActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jLabel22.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel22.setText("Supplier");

        txt_noRetur.setEnabled(false);
        txt_noRetur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_noReturMouseClicked(evt);
            }
        });
        txt_noRetur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_noReturKeyPressed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel24.setText("Alamat");

        jLabel19.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel19.setText("Jenis");

        jCheckBox1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCheckBox1.setForeground(new java.awt.Color(153, 0, 0));
        jCheckBox1.setText("Cetak LSG");

        jLabel32.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel32.setText("Nama Kasir");

        jLabel29.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel29.setText("Kasir");

        txt_total.setBackground(new java.awt.Color(204, 255, 204));
        txt_total.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_total.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_totalMouseClicked(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel30.setText("Total");

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel31.setText("No.Beli");

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel33.setText("Tgl. Retur");

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel34.setText("No.Retur");

        jLabel35.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel35.setText("Nama");

        jLabel68.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_stock_save_20659.png"))); // NOI18N
        jLabel68.setText("F12-Save");
        jLabel68.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel68MouseClicked(evt);
            }
        });

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel69.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel69.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Clear-icon.png"))); // NOI18N
        jLabel69.setText("F9-Clear");

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel70.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_yast_printer_30297.png"))); // NOI18N
        jLabel70.setText("Print");

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel71.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel71.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/cancel (3).png"))); // NOI18N
        jLabel71.setText("Esc-Exit");

        txt_namaSupply.setBackground(new java.awt.Color(204, 255, 204));
        txt_namaSupply.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_namaSupply.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_namaSupplyMouseClicked(evt);
            }
        });
        txt_namaSupply.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_namaSupplyKeyPressed(evt);
            }
        });

        txt_almtSupply.setBackground(new java.awt.Color(204, 255, 204));
        txt_almtSupply.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_almtSupply.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_almtSupplyMouseClicked(evt);
            }
        });
        txt_almtSupply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_almtSupplyActionPerformed(evt);
            }
        });
        txt_almtSupply.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_almtSupplyKeyPressed(evt);
            }
        });

        txt_Keterangan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_KeteranganMouseClicked(evt);
            }
        });
        txt_Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_KeteranganKeyPressed(evt);
            }
        });

        comJenis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BY FAKTUR BELI", "FAKTUR BEBAS" }));
        comJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comJenisActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel26.setText("Keterangan");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_document_delete_61766.png"))); // NOI18N
        jLabel1.setText("Delete");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);

        tbl_Pembelian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No.", "Kode", "Barang", "Lokasi", "Satuan", "Jumlah", "Harga", "Sub Total", "Diskon %", "Diskon Rp", "Diskon-2 %", "Diskon-2 Rp", "Harga Jadi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false, false, false, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_Pembelian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_PembelianMouseClicked(evt);
            }
        });
        tbl_Pembelian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_PembelianKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_Pembelian);
        if (tbl_Pembelian.getColumnModel().getColumnCount() > 0) {
            tbl_Pembelian.getColumnModel().getColumn(0).setResizable(false);
            tbl_Pembelian.getColumnModel().getColumn(1).setResizable(false);
            tbl_Pembelian.getColumnModel().getColumn(2).setResizable(false);
            tbl_Pembelian.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(comTableBarang));
            tbl_Pembelian.getColumnModel().getColumn(3).setResizable(false);
            tbl_Pembelian.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(comTableLokasi));
            tbl_Pembelian.getColumnModel().getColumn(4).setResizable(false);
            tbl_Pembelian.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(comTableKonv));
            tbl_Pembelian.getColumnModel().getColumn(5).setResizable(false);
            tbl_Pembelian.getColumnModel().getColumn(6).setResizable(false);
            tbl_Pembelian.getColumnModel().getColumn(7).setResizable(false);
            tbl_Pembelian.getColumnModel().getColumn(8).setResizable(false);
            tbl_Pembelian.getColumnModel().getColumn(9).setResizable(false);
            tbl_Pembelian.getColumnModel().getColumn(10).setResizable(false);
            tbl_Pembelian.getColumnModel().getColumn(11).setResizable(false);
            tbl_Pembelian.getColumnModel().getColumn(12).setResizable(false);
        }

        jLabel27.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel27.setText("Diskon Rp");

        jLabel28.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel28.setText("Diskon %");

        txt_diskonPersen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_diskonPersenMouseClicked(evt);
            }
        });
        txt_diskonPersen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_diskonPersenKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_diskonPersenKeyTyped(evt);
            }
        });

        txt_diskonRp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_diskonRpMouseClicked(evt);
            }
        });
        txt_diskonRp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_diskonRpKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_diskonRpKeyTyped(evt);
            }
        });

        comFakturBeli.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Kode Pembelian" }));
        comFakturBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comFakturBeliActionPerformed(evt);
            }
        });
        comFakturBeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                comFakturBeliKeyPressed(evt);
            }
        });

        txt_tanggal.setEnabled(false);

        txt_Supplier.setBackground(new java.awt.Color(204, 255, 204));
        txt_Supplier.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_Supplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_SupplierMouseClicked(evt);
            }
        });
        txt_Supplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_SupplierKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel34)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                                .addComponent(txt_noRetur, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(jLabel19))
                                    .addComponent(jLabel31)
                                    .addComponent(jLabel33))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(39, 39, 39)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(comJenis, 0, 174, Short.MAX_VALUE)
                                            .addComponent(comFakturBeli, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGap(40, 40, 40)
                                        .addComponent(txt_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel24)
                            .addComponent(jLabel35)
                            .addComponent(jLabel26))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_namaSupply, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_Supplier, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel27)
                                        .addGap(19, 19, 19)
                                        .addComponent(txt_diskonRp, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel28)
                                        .addGap(23, 23, 23)
                                        .addComponent(txt_diskonPersen, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(96, 96, 96))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_almtSupply, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txt_Keterangan)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel68)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel69)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel70)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel71)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addGap(35, 35, 35)
                                .addComponent(jLabel32)
                                .addGap(220, 220, 220)
                                .addComponent(jCheckBox1)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator5)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jSeparator2)
                                .addComponent(jLabel68, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel70, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSeparator4)
                                .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jSeparator3))
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel19)
                                .addComponent(comJenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(2, 2, 2))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(txt_Supplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel28)
                            .addGap(2, 2, 2))
                        .addComponent(txt_diskonPersen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(comFakturBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel33)
                            .addComponent(txt_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_noRetur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_namaSupply, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel35)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_diskonRp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel27))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel24)
                            .addComponent(txt_almtSupply, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(txt_Keterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel32)
                    .addComponent(jCheckBox1))
                .addGap(46, 46, 46))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 547, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1059, 585));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void comFakturBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comFakturBeliActionPerformed

        DefaultTableModel model = (DefaultTableModel) tbl_Pembelian.getModel();
        int baris = tbl_Pembelian.getRowCount();
        tabelModel = tbl_Pembelian.getModel();
        int no = 1;
        int selectedRow = tbl_Pembelian.getSelectedRow();
        ClearTable();
        try {
            String sql = "SELECT * FROM detail_pembelian , lokasi, barang_konversi, konversi WHERE detail_pembelian.kode_lokasi = lokasi.kode_lokasi and "
                    + "detail_pembelian.kode_konversi=barang_konversi.kode_barang_konversi and barang_konversi.kode_konversi = konversi.kode_konversi and "
                    + "detail_pembelian.no_faktur_pembelian = '" + comFakturBeli.getSelectedItem() + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {

                model.addRow(new Object[]{
                    no++,
                    res.getString("kode_barang"),
                    res.getString("nama_barang_edit"),
                    res.getString("nama_lokasi"),
                    res.getString("nama_konversi"),
                    "0",
                    res.getString("harga_pembelian"),
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        };
        try {
            String sql = "SELECT pembelian.kode_pembelian, supplier.nama_supplier, supplier.alamat_supplier FROM "
                    + "pembelian, supplier where  pembelian.kode_supplier = supplier.kode_supplier AND "
                    + "no_faktur_pembelian = '" + comFakturBeli.getSelectedItem() + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String namaS = res.getString(2);
                String alamatS = res.getString(3);
                txt_Supplier.setText(namaS);
                txt_namaSupply.setText(namaS);
                txt_almtSupply.setText(alamatS);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        };
    }//GEN-LAST:event_comFakturBeliActionPerformed

    private void txt_diskonRpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_diskonRpKeyPressed
        int key = evt.getKeyCode();
        if (key == 10) {
            txt_Keterangan.requestFocus();
        }
    }//GEN-LAST:event_txt_diskonRpKeyPressed

    private void txt_diskonRpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_diskonRpMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_diskonRpMouseClicked

    private void txt_diskonPersenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_diskonPersenKeyPressed
        int key = evt.getKeyCode();
        if (key == 10) {
            txt_diskonRp.requestFocus();
        }
    }//GEN-LAST:event_txt_diskonPersenKeyPressed

    private void txt_diskonPersenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_diskonPersenMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_diskonPersenMouseClicked

    private void tbl_PembelianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_PembelianMouseClicked
//       Pembelian_KotakHistoriBarang his = new Pembelian_KotakHistoriBarang();

    }//GEN-LAST:event_tbl_PembelianMouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        int hapus = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin Menghapus data ini ?", "Konfimasi Tolak Hapus Data", JOptionPane.OK_CANCEL_OPTION);
        if (hapus == JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(null, "Data sudah dihapus.");
        }
    }//GEN-LAST:event_jLabel1MouseClicked

    private void txt_KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_KeteranganKeyPressed
        int key = evt.getKeyCode();
        if (key == 10) {
            txt_diskonPersen.requestFocus();
        }
    }//GEN-LAST:event_txt_KeteranganKeyPressed

    private void txt_KeteranganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_KeteranganMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_KeteranganMouseClicked

    private void txt_almtSupplyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_almtSupplyKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_almtSupplyKeyPressed

    private void txt_almtSupplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_almtSupplyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_almtSupplyActionPerformed

    private void txt_almtSupplyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_almtSupplyMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_almtSupplyMouseClicked

    private void txt_namaSupplyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_namaSupplyKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaSupplyKeyPressed

    private void txt_namaSupplyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_namaSupplyMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaSupplyMouseClicked

    private void jLabel68MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel68MouseClicked
        int hapus = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin Menyimpan data ini ?", "Konfimasi simpan Data", JOptionPane.OK_CANCEL_OPTION);
        if (hapus == JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(null, "Data sudah disimpan.");
        }
    }//GEN-LAST:event_jLabel68MouseClicked

    private void txt_totalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_totalMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalMouseClicked

    private void txt_noReturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_noReturKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_noReturKeyPressed

    private void txt_noReturMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_noReturMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_noReturMouseClicked

    private void comTableBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableBarangActionPerformed

    }//GEN-LAST:event_comTableBarangActionPerformed

    private void comJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comJenisActionPerformed
        loadComboJenis();
    }//GEN-LAST:event_comJenisActionPerformed

    private void txt_diskonPersenKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_diskonPersenKeyTyped
        char angka = evt.getKeyChar();
        if (!(Character.isDigit(angka))) {
            evt.consume();
        }
        char enter = evt.getKeyChar();
        if (txt_diskonPersen.getText().length() > 2) {
            evt.consume();
            // JOptionPane.showMessageDialog(null, "Maaf Harus Angka ");
        }
        if (!(Character.isDigit(enter))) {
            evt.consume();
        }

    }//GEN-LAST:event_txt_diskonPersenKeyTyped

    private void txt_diskonRpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_diskonRpKeyTyped
        char angka = evt.getKeyChar();
        if (!(Character.isDigit(angka))) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_diskonRpKeyTyped

    private void tbl_PembelianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_PembelianKeyPressed

    }//GEN-LAST:event_tbl_PembelianKeyPressed

    private void comTableKonvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableKonvActionPerformed

    }//GEN-LAST:event_comTableKonvActionPerformed

    private void comTableLokasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableLokasiActionPerformed

    }//GEN-LAST:event_comTableLokasiActionPerformed

    private void comFakturBeliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comFakturBeliKeyPressed

    }//GEN-LAST:event_comFakturBeliKeyPressed

    private void txt_SupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_SupplierMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_SupplierMouseClicked

    private void txt_SupplierKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_SupplierKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_SupplierKeyPressed

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
                if ("windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Pembelian_Return.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pembelian_Return.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pembelian_Return.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pembelian_Return.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pembelian_Return().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comFakturBeli;
    private javax.swing.JComboBox<String> comJenis;
    private javax.swing.JComboBox<String> comTableBarang;
    private javax.swing.JComboBox<String> comTableKonv;
    private javax.swing.JComboBox<String> comTableLokasi;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTable tbl_Pembelian;
    private javax.swing.JTextField txt_Keterangan;
    private javax.swing.JTextField txt_Supplier;
    private javax.swing.JTextField txt_almtSupply;
    private javax.swing.JTextField txt_diskonPersen;
    private javax.swing.JTextField txt_diskonRp;
    private javax.swing.JTextField txt_namaSupply;
    private javax.swing.JTextField txt_noRetur;
    private javax.swing.JTextField txt_tanggal;
    private javax.swing.JTextField txt_total;
    // End of variables declaration//GEN-END:variables
}
