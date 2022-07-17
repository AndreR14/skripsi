-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 17, 2022 at 05:16 PM
-- Server version: 10.4.13-MariaDB
-- PHP Version: 7.4.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `skripsi`
--

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE `barang` (
  `id` varchar(100) NOT NULL,
  `nama_barang` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`id`, `nama_barang`) VALUES
('E00001', 'Ms Glow'),
('E00002', 'CItra');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `Id` varchar(100) NOT NULL,
  `Nama` varchar(100) NOT NULL,
  `Jenis_kelamin` varchar(100) NOT NULL,
  `Nomor_telp` varchar(100) NOT NULL,
  `Alamat` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`Id`, `Nama`, `Jenis_kelamin`, `Nomor_telp`, `Alamat`) VALUES
('A00001', 'Andre', 'Laki - Laki', '081753816', 'Gedong');

-- --------------------------------------------------------

--
-- Table structure for table `kategori`
--

CREATE TABLE `kategori` (
  `kode_kategori` varchar(100) NOT NULL,
  `nama_kategori` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `kategori`
--

INSERT INTO `kategori` (`kode_kategori`, `nama_kategori`) VALUES
('D00001', 'botol'),
('D00002', 'Bubuk');

-- --------------------------------------------------------

--
-- Table structure for table `pembelianbarang`
--

CREATE TABLE `pembelianbarang` (
  `kodebeli` varchar(100) NOT NULL,
  `kodestock` varchar(100) NOT NULL,
  `kodesupplier` varchar(100) NOT NULL,
  `kodekat` varchar(100) NOT NULL,
  `kodebarang` varchar(100) NOT NULL,
  `kodesatuan` varchar(100) NOT NULL,
  `jumlahbarang` int(100) NOT NULL,
  `hargabeli` int(100) NOT NULL,
  `total` int(100) NOT NULL,
  `tanggal` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pembelianbarang`
--

INSERT INTO `pembelianbarang` (`kodebeli`, `kodestock`, `kodesupplier`, `kodekat`, `kodebarang`, `kodesatuan`, `jumlahbarang`, `hargabeli`, `total`, `tanggal`) VALUES
('F00001', 'G00001', 'C00001', 'D00002', 'E00001', 'B00001', 10, 10000, 100000, '2022-03-05'),
('F00002', 'G00002', 'C00001', 'D00001', 'E00002', 'B00001', 12, 13, 156, '2022-03-05'),
('F00003', 'G00003', 'C00001', 'D00001', 'E00002', 'B00002', 1, 1, 1, '2022-03-05');

-- --------------------------------------------------------

--
-- Table structure for table `penjualan`
--

CREATE TABLE `penjualan` (
  `faktur` varchar(100) NOT NULL,
  `kodepelanggan` varchar(6) NOT NULL,
  `kodebarang` varchar(12) NOT NULL,
  `kodesatuan` varchar(6) NOT NULL,
  `hargajual` int(12) NOT NULL,
  `jumlahjual` int(4) NOT NULL,
  `hargajualtotal` int(12) NOT NULL,
  `bayar` int(12) NOT NULL,
  `kembali` int(12) NOT NULL,
  `untung` int(12) NOT NULL,
  `tanggal` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `penjualan`
--

INSERT INTO `penjualan` (`faktur`, `kodepelanggan`, `kodebarang`, `kodesatuan`, `hargajual`, `jumlahjual`, `hargajualtotal`, `bayar`, `kembali`, `untung`, `tanggal`) VALUES
('H00001', 'A00001', 'E00001', 'B00001', 11000, 9, 99000, 100000, 1000, 9000, '2022-03-05'),
('H00002', 'A00001', 'E00002', 'B00001', 15, 1, 15, 100, 85, 2, '2022-03-05');

-- --------------------------------------------------------

--
-- Table structure for table `regis`
--

CREATE TABLE `regis` (
  `Nama` varchar(100) NOT NULL,
  `Username` varchar(100) NOT NULL,
  `Password` varchar(100) NOT NULL,
  `Repass` varchar(100) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Nomor` int(100) NOT NULL,
  `role` enum('Admin','Direktur','Manajer','') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `regis`
--

INSERT INTO `regis` (`Nama`, `Username`, `Password`, `Repass`, `Email`, `Nomor`, `role`) VALUES
('a', 'a', '1', '1', 'w', 1, 'Admin'),
('Andre', 'ab', '1', '1', 'aa', 18212, 'Admin');

-- --------------------------------------------------------

--
-- Table structure for table `satuan`
--

CREATE TABLE `satuan` (
  `kodesatuan` varchar(100) NOT NULL,
  `jenisatuan` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `satuan`
--

INSERT INTO `satuan` (`kodesatuan`, `jenisatuan`) VALUES
('B00001', 'Kilo'),
('B00002', 'Gram');

-- --------------------------------------------------------

--
-- Table structure for table `stok`
--

CREATE TABLE `stok` (
  `kodestok` varchar(100) NOT NULL,
  `kodekat` varchar(100) NOT NULL,
  `kodebarang` varchar(100) NOT NULL,
  `kodesatuan` varchar(100) NOT NULL,
  `jumlahbarang` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `stok`
--

INSERT INTO `stok` (`kodestok`, `kodekat`, `kodebarang`, `kodesatuan`, `jumlahbarang`) VALUES
('G00001', 'D00001', 'E00001', 'B00001', 10),
('G00002', 'D00002', 'E00002', 'B00002', 12),
('G00003', 'D00003', 'E00002', 'B00003', 1);

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `Kode_supplier` varchar(6) NOT NULL,
  `Nama_supplier` varchar(100) NOT NULL,
  `Alamat` varchar(100) NOT NULL,
  `Nomor_telp` varchar(100) NOT NULL,
  `norek` varchar(100) NOT NULL,
  `Bank` varchar(100) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Tanggal` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`Kode_supplier`, `Nama_supplier`, `Alamat`, `Nomor_telp`, `norek`, `Bank`, `Email`, `Tanggal`) VALUES
('C00001', 'Prima Jaya', 'Jl Raya Tengah ', '0811212341', '081827', 'BRI', 'primajaya@gmail.com', '2022-03-05'),
('C00002', 'Mulia Sentosa', 'jonggol', '081233161', '46154119', 'BCA', 'Muliasentosa@gmail,com', '2022-03-05');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `kategori`
--
ALTER TABLE `kategori`
  ADD PRIMARY KEY (`kode_kategori`);

--
-- Indexes for table `pembelianbarang`
--
ALTER TABLE `pembelianbarang`
  ADD PRIMARY KEY (`kodebeli`);

--
-- Indexes for table `penjualan`
--
ALTER TABLE `penjualan`
  ADD PRIMARY KEY (`faktur`);

--
-- Indexes for table `regis`
--
ALTER TABLE `regis`
  ADD PRIMARY KEY (`Username`);

--
-- Indexes for table `satuan`
--
ALTER TABLE `satuan`
  ADD PRIMARY KEY (`kodesatuan`);

--
-- Indexes for table `stok`
--
ALTER TABLE `stok`
  ADD PRIMARY KEY (`kodestok`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`Kode_supplier`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
