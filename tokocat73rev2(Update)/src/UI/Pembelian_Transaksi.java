/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import java.sql.Connection;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumn;
import com.sun.glass.events.KeyEvent;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import UI.Pembelian_KotakHistoriBarang;

/**
 *
 * @author User
 */
public final class Pembelian_Transaksi extends javax.swing.JFrame {

    public String totalclone;
    public Float Tempharga;

    public float subtotal1 = 0, hargajadi1 = 0, diskonke21 = 0, totalqty = 0, total = 0, diskon = 0, diskonp1 = 0, diskonp11, diskonp21 = 0; //penjumlahan
    public float jumlah = 0, harga = 0, subtotal = 0, diskonp = 0, diskonrp = 0, diskonp2 = 0, diskonrp2 = 0, hargajadi = 0;//panggil colom tabel
    public float qty = 0;

    public Pembelian_Transaksi() {
        initComponents();
        this.setVisible(true);
        AutoCompleteDecorator.decorate(comSupplier);
        AutoCompleteDecorator.decorate(comTableBarang);
        AutoCompleteDecorator.decorate(comTableKonv);
        AutoCompleteDecorator.decorate(comTableLokasi);
        loadSupplier();
        loadComTableBarang();
        loadComTableLokasi();
        loadNumberTable();
//        tanggal_jam_sekarang();
        autonumber();
        AturlebarKolom();

    }

    void loadSupplier() {

        try {
            String sql = "select * from supplier";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String name = res.getString(2);
                comSupplier.addItem(name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }

    void loadComTableBarang() {
        try {
            String sql = "select * from barang order by nama_barang asc";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String name = res.getString(4);
                comTableBarang.addItem(name);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }

    void loadComTableLokasi() {
        try {
            String sql = "select * from lokasi";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String name = res.getString(2);
                comTableLokasi.addItem(name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }

    }

//    void loadComTableSatuan() {
//        try {
//            String sql = "select * from konversi order by kode_koversi asc";
//            java.sql.Connection conn = (Connection) Koneksi.configDB();
//            java.sql.Statement stm = conn.createStatement();
//            java.sql.ResultSet res = stm.executeQuery(sql);
//            while (res.next()) {
//                String name = res.getString(6);
//                comTableBarang.addItem(name);
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Eror" + e);
//        }
//   }
    void loadNumberTable() {
        int baris = tbl_Pembelian.getRowCount();
        for (int a = 0; a < baris; a++) {
            String nomor = String.valueOf(a + 1);
            tbl_Pembelian.setValueAt(nomor + ".", a, 0);
        }

    }

    void BersihField() {
        txt_inv.setText("");
        tgl_inv.setCalendar(null);
        txt_diskon.setText("");
        txt_diskonRp.setText("");
    }

    void AturlebarKolom() {
        TableColumn column;
        tbl_Pembelian.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tbl_Pembelian.getColumnModel().getColumn(0);
        column.setPreferredWidth(30);
        column = tbl_Pembelian.getColumnModel().getColumn(1);
        column.setPreferredWidth(40);
        column = tbl_Pembelian.getColumnModel().getColumn(2);
        column.setPreferredWidth(200);
        column = tbl_Pembelian.getColumnModel().getColumn(3);
        column.setPreferredWidth(70);
        column = tbl_Pembelian.getColumnModel().getColumn(4);
        column.setPreferredWidth(60);
        column = tbl_Pembelian.getColumnModel().getColumn(5);
        column.setPreferredWidth(50);
        column = tbl_Pembelian.getColumnModel().getColumn(6);
        column.setPreferredWidth(120);
        column = tbl_Pembelian.getColumnModel().getColumn(7);
        column.setPreferredWidth(140);
        column = tbl_Pembelian.getColumnModel().getColumn(8);
        column.setPreferredWidth(60);
        column = tbl_Pembelian.getColumnModel().getColumn(9);
        column.setPreferredWidth(60);
        column = tbl_Pembelian.getColumnModel().getColumn(10);
        column.setPreferredWidth(60);
        column = tbl_Pembelian.getColumnModel().getColumn(11);
        column.setPreferredWidth(100);

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
                    txt_tgl.setText(tahun + " - " + (bulan + 1) + " - " + hari + " " + jam + ":" + menit + ":" + detik);

                }
            }
        };
        p.start();
    }

    public void autonumber() {
        try {
            String sql = "select max(no_faktur_pembelian) from pembelian ORDER BY no_faktur_pembelian DESC";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                if (res.first() == false) {
                    txt_noNota.setText("PB");

                } else {
                    res.last();
                    String auto_num = res.getString(1);
                    String no = String.valueOf(auto_num);
                    //  int noLong = no.length();
                    //MENGATUR jumlah 0
                    String huruf = String.valueOf(auto_num.substring(1, 5));
                    int angka = Integer.valueOf(auto_num.substring(5)) + 1;
                    txt_noNota.setText(String.valueOf(huruf + "" + angka));

                }
            }
            res.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR: \n" + ex.toString(),
                    "Kesalahan", JOptionPane.WARNING_MESSAGE);
        }
    }

    void totalhargajadi() {
        int jumlahBaris = tbl_Pembelian.getRowCount();
        int totalBiaya = 0;
        int jumlahBarang, hargaBarang;
        TableModel tabelModel;
        tabelModel = tbl_Pembelian.getModel();
        for (int i = 0; i < jumlahBaris; i++) {
            jumlahBarang = Integer.parseInt(tabelModel.getValueAt(i, 0).toString());
            hargaBarang = Integer.parseInt(tabelModel.getValueAt(i, 1).toString());
            totalBiaya = totalBiaya + (jumlahBarang * hargaBarang);
        }
        txt_tbl_total.setText(String.valueOf(totalBiaya));
    }

    void hapussemuatabel() {
        int Hapus = JOptionPane.showConfirmDialog(null, "Apakah anda yakin mau menghapus semua data di tabel", "konfirmasi", JOptionPane.YES_NO_OPTION);
        if (Hapus == 0) {
            DefaultTableModel model = (DefaultTableModel) tbl_Pembelian.getModel();
            for (int i = tbl_Pembelian.getRowCount() - 1; i > -1; i--) {
                model.removeRow(i);

            }
            model.addRow(new Object[]{"", "", "", "", "", "", "", "", "", "", "", "", ""});
        }
    }

    static String rptabel(String b) {
        b = b.replace(",", "");
        b = NumberFormat.getNumberInstance(Locale.getDefault()).format(Double.parseDouble(b));
        return b;
    }

    static String rptabelkembali(String b) {
        b = b.replace(",", "");

        return b;
    }

    void dpnya() {
        float t = Float.parseFloat(totalclone);
        float dp = Float.parseFloat(txt_dp.getText());
        String h = String.valueOf(t - dp);
        System.out.println(t + " : " + dp + " : " + h);
        txt_tbl_total.setText(h);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comTableBarang = new javax.swing.JComboBox();
        comTableKonv = new javax.swing.JComboBox<>();
        comTableLokasi = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        txt_rekSupply = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txt_noNota = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        com_faktur = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_Pembelian = new javax.swing.JTable();
        txt_jumQty = new javax.swing.JTextField();
        txt_jumItem = new javax.swing.JTextField();
        chk_cetakSlg = new javax.swing.JCheckBox();
        lbl_nmKasir = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txt_tbl_total = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        txt_nmSupply = new javax.swing.JTextField();
        txt_almtSupply = new javax.swing.JTextField();
        txt_ket = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        com_top = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        txt_tgl = new javax.swing.JTextField();
        LabelSave = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        LabelClear = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        LabelPrint = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        LabelExit = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        LabelPrev = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        LabelNext = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        txt_inv = new javax.swing.JTextField();
        txt_diskon = new javax.swing.JTextField();
        txt_diskonRp = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        chk_fakturSupply = new javax.swing.JCheckBox();
        txt_dp = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        com_jenisKeuangan = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        comSupplier = new javax.swing.JComboBox();
        tgl_inv = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();

        comTableBarang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "tesssssss" }));
        comTableBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comTableBarangMouseClicked(evt);
            }
        });
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
        jLabel22.setForeground(new java.awt.Color(0, 0, 255));
        jLabel22.setText("Faktur Beli");

        txt_rekSupply.setEditable(false);
        txt_rekSupply.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_rekSupplyMouseClicked(evt);
            }
        });
        txt_rekSupply.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_rekSupplyKeyPressed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 255));
        jLabel24.setText("Tanggal");

        txt_noNota.setEnabled(false);

        jLabel21.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel21.setText("Keterangan");

        com_faktur.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FAKTUR BELI", "BY ORDER BELI" }));
        com_faktur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                com_fakturActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel19.setText("Supplier");

        jLabel23.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 255));
        jLabel23.setText("Inv #");

        jLabel27.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel27.setText("Diskon Rp");

        jLabel26.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel26.setText("Diskon %");

        tbl_Pembelian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, "", null, "", "", null, "", "", null, "", null}
            },
            new String [] {
                "No.", "Kode", "Barang", "Lokasi", "Satuan", "Jumlah", "Harga", "Sub Total", "Diskon %", "Diskon Rp", "Diskon-2 %", "Diskon-2 Rp", "Harga Jadi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true, true, true, false, true, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_Pembelian.setToolTipText("");
        tbl_Pembelian.setRequestFocusEnabled(false);
        tbl_Pembelian.setRowSelectionAllowed(false);
        tbl_Pembelian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_PembelianMouseClicked(evt);
            }
        });
        tbl_Pembelian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_PembelianKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbl_PembelianKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tbl_PembelianKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_Pembelian);
        if (tbl_Pembelian.getColumnModel().getColumnCount() > 0) {
            tbl_Pembelian.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(comTableBarang));
            tbl_Pembelian.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(comTableLokasi));
            tbl_Pembelian.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(comTableKonv));
        }

        txt_jumQty.setBackground(new java.awt.Color(0, 0, 0));
        txt_jumQty.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_jumQty.setForeground(new java.awt.Color(255, 204, 0));
        txt_jumQty.setText("Jumlah Qty");
        txt_jumQty.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_jumQty.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_jumQtyMouseClicked(evt);
            }
        });

        txt_jumItem.setBackground(new java.awt.Color(0, 0, 0));
        txt_jumItem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_jumItem.setForeground(new java.awt.Color(255, 204, 0));
        txt_jumItem.setText("Jumlah Item");
        txt_jumItem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_jumItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_jumItemMouseClicked(evt);
            }
        });

        chk_cetakSlg.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        chk_cetakSlg.setForeground(new java.awt.Color(153, 0, 0));
        chk_cetakSlg.setText("Cetak LSG");

        lbl_nmKasir.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lbl_nmKasir.setText("Nama Kasir");

        jLabel29.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel29.setText("Kasir");

        txt_tbl_total.setBackground(new java.awt.Color(0, 0, 0));
        txt_tbl_total.setForeground(new java.awt.Color(255, 255, 255));
        txt_tbl_total.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        txt_tbl_total.setEnabled(false);
        txt_tbl_total.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_tbl_totalMouseClicked(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel30.setText("Total");

        txt_nmSupply.setEditable(false);
        txt_nmSupply.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_nmSupplyMouseClicked(evt);
            }
        });
        txt_nmSupply.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nmSupplyKeyPressed(evt);
            }
        });

        txt_almtSupply.setEditable(false);
        txt_almtSupply.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_almtSupplyMouseClicked(evt);
            }
        });
        txt_almtSupply.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_almtSupplyKeyPressed(evt);
            }
        });

        txt_ket.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_ketMouseClicked(evt);
            }
        });
        txt_ket.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_ketKeyPressed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(204, 0, 0));
        jLabel31.setText("Nama");

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(204, 0, 0));
        jLabel33.setText("Alamat");

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(204, 0, 0));
        jLabel34.setText("Rekening");

        jLabel28.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 255));
        jLabel28.setText("Tgl Inv");

        jLabel25.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 255));
        jLabel25.setText("TOP");

        com_top.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TUNAI", "120 HARI", "90 HARI", "75", "60", "45", "30", "14", "7" }));
        com_top.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                com_topActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 0, 255));
        jLabel35.setText("No.Nota");

        txt_tgl.setEditable(false);

        LabelSave.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        LabelSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_stock_save_20659.png"))); // NOI18N
        LabelSave.setText("F12-Save");

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        LabelClear.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        LabelClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Clear-icon.png"))); // NOI18N
        LabelClear.setText("F9-Clear");
        LabelClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LabelClearMouseClicked(evt);
            }
        });
        LabelClear.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LabelClearKeyPressed(evt);
            }
        });

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        LabelPrint.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        LabelPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/if_yast_printer_30297.png"))); // NOI18N
        LabelPrint.setText("Print");

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        LabelExit.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        LabelExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/cancel (3).png"))); // NOI18N
        LabelExit.setText("Esc-Exit");

        LabelPrev.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        LabelPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/back-icon.png"))); // NOI18N
        LabelPrev.setText("Prev");
        LabelPrev.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LabelPrevMouseClicked(evt);
            }
        });

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);

        LabelNext.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        LabelNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/forward-icon.png"))); // NOI18N
        LabelNext.setText("Next");
        LabelNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LabelNextMouseClicked(evt);
            }
        });

        txt_inv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_invMouseClicked(evt);
            }
        });
        txt_inv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_invActionPerformed(evt);
            }
        });
        txt_inv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_invKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_invKeyTyped(evt);
            }
        });

        txt_diskon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_diskonMouseClicked(evt);
            }
        });
        txt_diskon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_diskonKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_diskonKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_diskonKeyTyped(evt);
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

        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);

        chk_fakturSupply.setText("Faktur Supplier Telah Diterima");

        txt_dp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_dpMouseClicked(evt);
            }
        });
        txt_dp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dpKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_dpKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_dpKeyTyped(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel36.setText("DP");

        com_jenisKeuangan.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        com_jenisKeuangan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BCA | 1", "MANDIRI | 2", "BRI | 3", "CAT73 | 4", "TOKOCAT73 | 5" }));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Jenis Keuangan");

        comSupplier.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Masukkan Nama Supplier --" }));
        comSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comSupplierActionPerformed(evt);
            }
        });

        tgl_inv.setDateFormatString(" yyyy - M - d");
        tgl_inv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tgl_invKeyPressed(evt);
            }
        });

        jButton1.setText("HAPUS");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(LabelSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LabelClear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LabelPrint)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(LabelExit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(LabelPrev)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(LabelNext)
                        .addGap(96, 96, 96)
                        .addComponent(jButton1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(chk_fakturSupply)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(com_jenisKeuangan, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel36)
                        .addGap(18, 18, 18)
                        .addComponent(txt_dp, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_tbl_total, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addGap(35, 35, 35)
                        .addComponent(lbl_nmKasir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chk_cetakSlg)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_jumItem, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_jumQty, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(301, 301, 301))
                    .addComponent(jSeparator6)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(jLabel19))
                                    .addComponent(jLabel31)
                                    .addComponent(jLabel33))
                                .addGap(39, 39, 39))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel34)
                                .addGap(36, 36, 36)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txt_rekSupply, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                            .addComponent(txt_nmSupply, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                            .addComponent(txt_almtSupply, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                            .addComponent(comSupplier, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 151, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel25)
                            .addComponent(jLabel24)
                            .addComponent(jLabel35))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(com_faktur, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_noNota)
                            .addComponent(com_top, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_tgl, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(105, 105, 105)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel26)
                                    .addComponent(jLabel28)
                                    .addComponent(jLabel23))
                                .addGap(23, 23, 23))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel27)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_diskon, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_inv, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txt_diskonRp, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tgl_inv, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(112, 112, 112))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(23, 23, 23)
                        .addComponent(txt_ket)
                        .addContainerGap())
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator2)
                    .addComponent(LabelSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelPrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator4)
                    .addComponent(LabelExit, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator5)
                    .addComponent(jSeparator7)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LabelNext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(LabelPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel19)
                                    .addComponent(comSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(2, 2, 2))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel22)
                                .addComponent(com_faktur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_nmSupply, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31)
                            .addComponent(jLabel35)
                            .addComponent(txt_noNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_almtSupply, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel33))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_rekSupply, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel34)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel24)
                                    .addComponent(txt_tgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel25)
                                    .addComponent(com_top, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(3, 3, 3))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel26)
                                .addGap(28, 28, 28))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_inv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel23))
                                .addGap(7, 7, 7)
                                .addComponent(tgl_inv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_diskon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_diskonRp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel27))))
                        .addGap(6, 6, 6)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_ket, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(com_jenisKeuangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_dp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel36))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel30)
                        .addComponent(txt_tbl_total, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(chk_fakturSupply)))
                .addGap(18, 18, 18)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_jumItem, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_jumQty, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(chk_cetakSlg))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel29)
                        .addComponent(lbl_nmKasir)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(1161, 682));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_rekSupplyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_rekSupplyMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rekSupplyMouseClicked

    private void txt_rekSupplyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_rekSupplyKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rekSupplyKeyPressed

    private void txt_jumQtyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_jumQtyMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumQtyMouseClicked

    private void txt_jumItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_jumItemMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumItemMouseClicked

    private void txt_tbl_totalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_tbl_totalMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tbl_totalMouseClicked

    private void txt_nmSupplyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_nmSupplyMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nmSupplyMouseClicked

    private void txt_nmSupplyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nmSupplyKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nmSupplyKeyPressed

    private void txt_almtSupplyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_almtSupplyMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_almtSupplyMouseClicked

    private void txt_almtSupplyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_almtSupplyKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_almtSupplyKeyPressed

    private void txt_ketMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_ketMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ketMouseClicked

    private void txt_ketKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ketKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ketKeyPressed

    private void com_topActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_com_topActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_com_topActionPerformed

    private void txt_invMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_invMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_invMouseClicked

    private void txt_diskonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_diskonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_diskonMouseClicked

    private void txt_diskonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_diskonKeyPressed
        int key = evt.getKeyCode();
        if (key == 10) {
            txt_diskonRp.requestFocus();
        }
    }//GEN-LAST:event_txt_diskonKeyPressed

    private void txt_diskonRpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_diskonRpMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_diskonRpMouseClicked

    private void txt_diskonRpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_diskonRpKeyPressed
        int key = evt.getKeyCode();
        if (key == 10) {
            txt_ket.requestFocus();
        }
    }//GEN-LAST:event_txt_diskonRpKeyPressed

    private void com_fakturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_com_fakturActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_com_fakturActionPerformed

    private void txt_dpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_dpMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dpMouseClicked

    private void txt_dpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dpKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            dpnya();
        }
    }//GEN-LAST:event_txt_dpKeyPressed

    private void comSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comSupplierActionPerformed
        try {
            String sql = "select * from supplier where nama_supplier = '" + comSupplier.getSelectedItem() + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String nama = res.getString(2);
                String alamat = res.getString(3);
                String rek = res.getString(9);
                txt_nmSupply.setText(nama);
                txt_almtSupply.setText(alamat);
                txt_rekSupply.setText(rek);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }//GEN-LAST:event_comSupplierActionPerformed

    private void comTableBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableBarangActionPerformed
        int kode_barang = 0;
        String kov;
        int baris = tbl_Pembelian.getRowCount();
        TableModel tabelModel;
        tabelModel = tbl_Pembelian.getModel();
        try {
            String sql = "select * from barang where nama_barang = '" + comTableBarang.getSelectedItem() + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String kode = res.getString(1);
                String harga = res.getString(11);
                String jumlah = "0";
                //String Konv = "";

                int selectedRow = tbl_Pembelian.getSelectedRow();
                if (selectedRow != -1) {
                    tbl_Pembelian.setValueAt(kode, selectedRow, 1);
                    //tbl_Pembelian.setValueAt(Konv, selectedRow, 4);
                    tbl_Pembelian.setValueAt(jumlah, selectedRow, 5);
                    tbl_Pembelian.setValueAt(harga, selectedRow, 6);
                    tbl_Pembelian.setValueAt(jumlah, selectedRow, 8);
                    tbl_Pembelian.setValueAt(jumlah, selectedRow, 9);
                    tbl_Pembelian.setValueAt(jumlah, selectedRow, 10);
                    tbl_Pembelian.setValueAt(jumlah, selectedRow, 11);
                    Tempharga = Float.valueOf(harga);

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror aaa" + e);
        }
        comTableKonv.removeAllItems();
        try {
            for (int i = 0; i < baris; i++) {
//                kode_barang = Integer.parseInt(tabelModel.getValueAt(i, 1).toString());
//                if (kode_barang != 0) {
//                    System.out.println("Kosong boss");
//                } else {
//                    System.out.println("eror nggak = " + kode_barang);
//                }

            }
            // System.out.println("ambil kode barang : " + kode_barang);
            String sql = "select k.kode_konversi, k.nama_konversi from konversi k, barang_konversi bk where k.kode_konversi = bk.kode_konversi and bk.kode_barang = '" + kode_barang + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String Konv = res.getString(2);
                comTableKonv.addItem(Konv);
            }
//            if (comTableKonv.getSelectedIndex() == 1) {
//                System.out.println("Satu");
//            }
//            if (comTableKonv.getSelectedIndex() == 2) {
//                System.out.println("Dua ");

        } catch (Exception e) {
            //           JOptionPane.showMessageDialog(null, "Eror nyaaaaa: ");
            
        }

    }//GEN-LAST:event_comTableBarangActionPerformed

    private void txt_diskonKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_diskonKeyTyped
        int diskon = 0;
        char enter = evt.getKeyChar();
        if (txt_diskon.getText().length() > 2) {
            evt.consume();
            // JOptionPane.showMessageDialog(null, "Maaf Harus Angka ");
        }
        if (!(Character.isDigit(enter))) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_diskonKeyTyped

    private void txt_diskonKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_diskonKeyReleased
//        String nana = txt_diskon.getText();
//        txt_diskonRp.setText(nana);
    }//GEN-LAST:event_txt_diskonKeyReleased

    private void txt_dpKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dpKeyReleased
//  TableModel tabelModel;
//            tabelModel = tbl_Pembelian.getModel();
//             float baris = tbl_Pembelian.getRowCount();
//            int i = 0;
//           float totaljadi,totaljadi1 = 0, pengurangandp;
//         
//            for (i = 0; i < baris; i++) {
////       
////                totaljadi = Float.parseFloat(tabelModel.getValueAt(i, 12).toString());
////                totaljadi1 += totaljadi;
//                txt_dp.getText();
//       
//                
//               
//            }
//    String tmpt = txt_tbl_total.getText();

        dpnya();
    }//GEN-LAST:event_txt_dpKeyReleased

    private void tbl_PembelianKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_PembelianKeyReleased
        DefaultTableModel model = (DefaultTableModel) tbl_Pembelian.getModel();
        float baris = tbl_Pembelian.getRowCount();

        if (tbl_Pembelian.getRowCount()
                >= 0) {
            int kov = 0;

            TableModel tabelModel;
            tabelModel = tbl_Pembelian.getModel();
            int i = 0;

            for (i = 0; i < baris; i++) {

                if (tabelModel.getValueAt(i, 8).toString().equals("")) {
                    diskonp = 0;
                } else {
                    diskonp = Float.parseFloat(tabelModel.getValueAt(i, 8).toString());
                }
                if (tabelModel.getValueAt(i, 9).toString().equalsIgnoreCase("")) {
                    diskonrp = 0;
                } else {
                    diskonrp = Float.parseFloat(tabelModel.getValueAt(i, 9).toString());
                }
                if (tabelModel.getValueAt(i, 10).toString().equals("")) {
                    diskonp2 = 0;
                } else {
                    diskonp2 = Float.parseFloat(tabelModel.getValueAt(i, 10).toString());
                }
                if (tabelModel.getValueAt(i, 11).toString().equals("")) {
                    diskonrp2 = 0;
                } else {
                    diskonrp2 = Float.parseFloat(tabelModel.getValueAt(i, 11).toString());
                }
                if (tabelModel.getValueAt(i, 5).toString().equals("")) {
                    jumlah = 0;
                } else {
                    jumlah = Float.parseFloat(tabelModel.getValueAt(i, 5).toString());
                }
//              
                harga = Float.parseFloat(tabelModel.getValueAt(i, 6).toString());
                subtotal1 = jumlah * harga;

//                    diskonp11 =  ((subtotal * diskonp)/100);
//                    diskonp21 =  ((subtotal * diskonp2)/100);
                diskon = ((diskonp + diskonp2) * subtotal1 / 100);

                hargajadi1 = subtotal1 - diskon - diskonrp - diskonrp2;

                total += hargajadi1;
                totalqty += jumlah;
            }

            model.setValueAt(rptabel(String.valueOf(subtotal1)), i - 1, 7);
            model.setValueAt(rptabel(String.valueOf(hargajadi1)), i - 1, 12);
            model.setValueAt(rptabel(String.valueOf(harga)), i - 1, 6);
//                model.setValueAt(rptabel(String.valueOf(diskonp)), i-1, 9);
//                 model.setValueAt(rptabel(String.valueOf(diskonp2)), i-1, 11); 
            model.setValueAt(rptabelkembali(String.valueOf(harga)), i - 1, 6);
//                   model.setValueAt(rptabelkembali(String.valueOf(diskonp)), i-1, 9); 
//                   model.setValueAt(rptabelkembali(String.valueOf(diskonp2)), i-1, 11); 
//            }
            txt_tbl_total.setText("" + total);
            txt_jumQty.setText("" + totalqty);
//                System.out.println("qty = " + totalqty);}
            totalclone = txt_tbl_total.getText();
            dpnya();

        }

//               
        loadNumberTable();

    }//GEN-LAST:event_tbl_PembelianKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        hapussemuatabel();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_invActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_invActionPerformed

    }//GEN-LAST:event_txt_invActionPerformed

    private void tbl_PembelianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_PembelianKeyPressed
        int kode_barang = 0;
        DefaultTableModel model = (DefaultTableModel) tbl_Pembelian.getModel();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {    // Membuat Perintah Saat Menekan Enter

            float baris = tbl_Pembelian.getRowCount();
            float subtotal1 = 0, hargajadi1 = 0, diskonke21 = 0, totalqty = 0, total = 0, diskon = 0, diskonp1 = 0, diskonp11, diskonp21 = 0; //penjumlahan
            float jumlah = 0, harga = 0, subtotal = 0, diskonp = 0, diskonrp = 0, diskonp2 = 0, diskonrp2 = 0, hargajadi = 0;//panggil colom tabel
            float qty = 0;
            TableModel tabelModel;
            tabelModel = tbl_Pembelian.getModel();
            int i = 0;

            for (i = 0; i < baris; i++) {

                if (tabelModel.getValueAt(i, 8).toString().equals("")) {
                    diskonp = 0;
                } else {
                    diskonp = Float.parseFloat(tabelModel.getValueAt(i, 8).toString());
                }
                if (tabelModel.getValueAt(i, 9).toString().equalsIgnoreCase("")) {
                    diskonrp = 0;
                } else {
                    diskonrp = Float.parseFloat(tabelModel.getValueAt(i, 9).toString());
                }
                if (tabelModel.getValueAt(i, 10).toString().equals("")) {
                    diskonp2 = 0;
                } else {
                    diskonp2 = Float.parseFloat(tabelModel.getValueAt(i, 10).toString());
                }
                if (tabelModel.getValueAt(i, 11).toString().equals("")) {
                    diskonrp2 = 0;
                } else {
                    diskonrp2 = Float.parseFloat(tabelModel.getValueAt(i, 11).toString());
                }
                if (tabelModel.getValueAt(i, 5).toString().equals("")) {
                    jumlah = 1;
                } else {
                    jumlah = Float.parseFloat(tabelModel.getValueAt(i, 5).toString());
                }
//              
                harga = Float.parseFloat(tabelModel.getValueAt(i, 6).toString());

                subtotal1 = jumlah * harga;

//                    diskonp11 =  ((subtotal * diskonp)/100);
//                    diskonp21 =  ((subtotal * diskonp2)/100);
                diskon = ((diskonp + diskonp2) * subtotal1 / 100);

                hargajadi1 = subtotal1 - diskon - diskonrp - diskonrp2;
                System.out.println("diskonp : " + diskonp);
                total += hargajadi1;
                totalqty += jumlah;
            }
            model.setValueAt(rptabel(String.valueOf(subtotal1)), i - 1, 7);
            model.setValueAt(rptabel(String.valueOf(hargajadi1)), i - 1, 12);
            model.setValueAt(rptabel(String.valueOf(harga)), i - 1, 6);
//                model.setValueAt(rptabel(String.valueOf(diskonp)), i-1, 9);
//                 model.setValueAt(rptabel(String.valueOf(diskonp2)), i-1, 11); 
            model.setValueAt(rptabelkembali(String.valueOf(harga)), i - 1, 6);
//                   model.setValueAt(rptabelkembali(String.valueOf(diskonp)), i-1, 9); 
//                   model.setValueAt(rptabelkembali(String.valueOf(diskonp2)), i-1, 11); 
//            }
            model.addRow(new Object[]{"", "", "", "", "", "", "", "", "", "", "", "", ""});
            txt_tbl_total.setText("" + total);
            txt_jumQty.setText("" + totalqty);
            dpnya();
        }

//             
//            }
//                System.out.println("qty = " + totalqty);}
        loadNumberTable();

//     

    }//GEN-LAST:event_tbl_PembelianKeyPressed

    private void txt_invKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_invKeyTyped

        char enter = evt.getKeyChar();
        if (!(Character.isDigit(enter))) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_invKeyTyped

    private void txt_diskonRpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_diskonRpKeyTyped
        char enter = evt.getKeyChar();
        if (!(Character.isDigit(enter))) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_diskonRpKeyTyped

    private void txt_dpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dpKeyTyped

    }//GEN-LAST:event_txt_dpKeyTyped

    private void comTableLokasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableLokasiActionPerformed
        try {
            String sql = "select * from lokasi where nama_lokasi = '" + comTableLokasi.getSelectedItem() + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                String id = res.getString(2);

                int selectedRow = tbl_Pembelian.getSelectedRow();
                if (selectedRow != -1) {
                    tbl_Pembelian.setValueAt(id, selectedRow, 3);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }//GEN-LAST:event_comTableLokasiActionPerformed

    private void LabelClearKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LabelClearKeyPressed

    }//GEN-LAST:event_LabelClearKeyPressed

    private void LabelClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LabelClearMouseClicked
        BersihField();
    }//GEN-LAST:event_LabelClearMouseClicked

    private void LabelNextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LabelNextMouseClicked

    }//GEN-LAST:event_LabelNextMouseClicked

    private void LabelPrevMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LabelPrevMouseClicked
        boolean isi = false;
        do {
            try {
                String sql = "select max(no_faktur_pembelian) from pembelian ORDER BY no_faktur_pembelian DESC";
                java.sql.Connection conn = (Connection) Koneksi.configDB();
                java.sql.Statement stm = conn.createStatement();
                java.sql.ResultSet res = stm.executeQuery(sql);
                while (res.next()) {
                    if (res.first() == false) {
                        txt_noNota.setText("PB");

                    } else {
                        res.last();
                        String auto_num = res.getString(1);
                        String no = String.valueOf(auto_num);
                        String huruf = String.valueOf(auto_num.substring(1, 5));
                        int angka = Integer.valueOf(auto_num.substring(5)) - 1;
                        txt_noNota.setText(String.valueOf(huruf + "" + angka));

                    }
                }
                res.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "ERROR: \n" + ex.toString(),
                        "Kesalahan", JOptionPane.WARNING_MESSAGE);
            }
        } while (evt.equals(txt_noNota != null));

    }//GEN-LAST:event_LabelPrevMouseClicked

    private void txt_invKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_invKeyPressed
        int key = evt.getKeyCode();
        if (key == 10) {
            txt_diskon.requestFocus();
        }
    }//GEN-LAST:event_txt_invKeyPressed

    private void tgl_invKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tgl_invKeyPressed
        int key = evt.getKeyCode();
        if (key == 10) {
            txt_diskon.requestFocus();
        }
    }//GEN-LAST:event_tgl_invKeyPressed

    private void comTableBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comTableBarangMouseClicked
//        String kov = "";
//        TableModel tabelModel;
//        tabelModel = tbl_Pembelian.getModel();
//        for (int i = 0; i < 10; i++) {
//            kov = String.valueOf(tabelModel.getValueAt(i, 4));
//        }
//        System.out.println("isi Kov " + kov);
//

    }//GEN-LAST:event_comTableBarangMouseClicked

    private void tbl_PembelianKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_PembelianKeyTyped
        TableColumnModel colom = tbl_Pembelian.getColumnModel();
        TableColumn coll = colom.getColumn(8);
        if (coll.toString().length() > 2) {
            evt.consume();
        }
        char enter = evt.getKeyChar();
        if (!(Character.isDigit(enter))) {
            evt.consume();
        }


    }//GEN-LAST:event_tbl_PembelianKeyTyped

    private void comTableKonvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comTableKonvActionPerformed
        DefaultTableModel model = (DefaultTableModel) tbl_Pembelian.getModel();
        int baris = tbl_Pembelian.getRowCount();
        TableModel tabelModel;
        tabelModel = tbl_Pembelian.getModel();
        int kode_barang = 0;
        try {
            for (int i = 0; i < baris; i++) {
                kode_barang = Integer.parseInt(tabelModel.getValueAt(i, 1).toString());
            }
            String sql = "select * from barang_konversi where kode_barang ='" + kode_barang + "'";
            java.sql.Connection conn = (Connection) Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            System.out.println("Kode" + kode_barang);
            while (res.next()) {
                String konvTemp = res.getString(2);
                int selectedRow = tbl_Pembelian.getSelectedRow();
                if ((comTableKonv.getSelectedIndex() != 0) && ((comTableKonv.getSelectedIndex() + 1) == res.getInt(5))) {
                    float temp = Tempharga * res.getInt(4);
                    model.setValueAt(String.valueOf(temp), 0, 6);
                    System.out.println(">0 " + temp);
                } else {
                    model.setValueAt(String.valueOf(Tempharga), 0, 6);
                    System.out.println("0 " + Tempharga);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror" + e);
        }
    }//GEN-LAST:event_comTableKonvActionPerformed

    private void tbl_PembelianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_PembelianMouseClicked
        int selectedRow = tbl_Pembelian.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) tbl_Pembelian.getModel();
        String Kode = String.valueOf(model.getValueAt(selectedRow, 1));
        tbl_Pembelian.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1 && table.getSelectedColumn() == 1) {

                    Pembelian_KotakHistoriBarang hb = new Pembelian_KotakHistoriBarang(Kode);
                    hb.getObj(1).setVisible(true);      
                }
            }
        });
    }//GEN-LAST:event_tbl_PembelianMouseClicked

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
            java.util.logging.Logger.getLogger(Pembelian_Transaksi.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pembelian_Transaksi.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pembelian_Transaksi.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pembelian_Transaksi.class
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pembelian_Transaksi().setVisible(true);

            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelClear;
    private javax.swing.JLabel LabelExit;
    private javax.swing.JLabel LabelNext;
    private javax.swing.JLabel LabelPrev;
    private javax.swing.JLabel LabelPrint;
    private javax.swing.JLabel LabelSave;
    private javax.swing.JCheckBox chk_cetakSlg;
    private javax.swing.JCheckBox chk_fakturSupply;
    private javax.swing.JComboBox comSupplier;
    private javax.swing.JComboBox comTableBarang;
    private javax.swing.JComboBox<String> comTableKonv;
    private javax.swing.JComboBox<String> comTableLokasi;
    private javax.swing.JComboBox<String> com_faktur;
    private javax.swing.JComboBox<String> com_jenisKeuangan;
    private javax.swing.JComboBox<String> com_top;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JLabel lbl_nmKasir;
    private javax.swing.JTable tbl_Pembelian;
    private com.toedter.calendar.JDateChooser tgl_inv;
    private javax.swing.JTextField txt_almtSupply;
    private javax.swing.JTextField txt_diskon;
    private javax.swing.JTextField txt_diskonRp;
    private javax.swing.JTextField txt_dp;
    private javax.swing.JTextField txt_inv;
    private javax.swing.JTextField txt_jumItem;
    private javax.swing.JTextField txt_jumQty;
    private javax.swing.JTextField txt_ket;
    private javax.swing.JTextField txt_nmSupply;
    private javax.swing.JTextField txt_noNota;
    private javax.swing.JTextField txt_rekSupply;
    private javax.swing.JTextField txt_tbl_total;
    private javax.swing.JTextField txt_tgl;
    // End of variables declaration//GEN-END:variables

    private String totalclone() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
