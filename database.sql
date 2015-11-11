USE [master]
GO
/****** Object:  Database [MATERIAL]    Script Date: 11/11/2015 15:44:43 ******/
CREATE DATABASE [MATERIAL]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'MATERIAL', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL11.MSSQLSERVER\MSSQL\DATA\MATERIAL.mdf' , SIZE = 5120KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'MATERIAL_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL11.MSSQLSERVER\MSSQL\DATA\MATERIAL_log.ldf' , SIZE = 26816KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [MATERIAL] SET COMPATIBILITY_LEVEL = 110
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [MATERIAL].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [MATERIAL] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [MATERIAL] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [MATERIAL] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [MATERIAL] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [MATERIAL] SET ARITHABORT OFF 
GO
ALTER DATABASE [MATERIAL] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [MATERIAL] SET AUTO_CREATE_STATISTICS ON 
GO
ALTER DATABASE [MATERIAL] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [MATERIAL] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [MATERIAL] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [MATERIAL] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [MATERIAL] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [MATERIAL] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [MATERIAL] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [MATERIAL] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [MATERIAL] SET  DISABLE_BROKER 
GO
ALTER DATABASE [MATERIAL] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [MATERIAL] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [MATERIAL] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [MATERIAL] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [MATERIAL] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [MATERIAL] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [MATERIAL] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [MATERIAL] SET RECOVERY FULL 
GO
ALTER DATABASE [MATERIAL] SET  MULTI_USER 
GO
ALTER DATABASE [MATERIAL] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [MATERIAL] SET DB_CHAINING OFF 
GO
ALTER DATABASE [MATERIAL] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [MATERIAL] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
EXEC sys.sp_db_vardecimal_storage_format N'MATERIAL', N'ON'
GO
USE [MATERIAL]
GO
/****** Object:  User [material]    Script Date: 11/11/2015 15:44:43 ******/
CREATE USER [material] FOR LOGIN [material] WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  User [GRUPO_ALTAMIRA\alessandro.holanda]    Script Date: 11/11/2015 15:44:44 ******/
CREATE USER [GRUPO_ALTAMIRA\alessandro.holanda] FOR LOGIN [GRUPO_ALTAMIRA\alessandro.holanda] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_datareader] ADD MEMBER [material]
GO
ALTER ROLE [db_datawriter] ADD MEMBER [material]
GO
ALTER ROLE [db_owner] ADD MEMBER [GRUPO_ALTAMIRA\alessandro.holanda]
GO
USE [MATERIAL]
GO
/****** Object:  Sequence [dbo].[IHM_LOG_SEQUENCE]    Script Date: 11/11/2015 15:44:44 ******/
CREATE SEQUENCE [dbo].[IHM_LOG_SEQUENCE] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
USE [MATERIAL]
GO
/****** Object:  Sequence [dbo].[MAQUINA_LOG_SEQUENCE]    Script Date: 11/11/2015 15:44:44 ******/
CREATE SEQUENCE [dbo].[MAQUINA_LOG_SEQUENCE] 
 AS [bigint]
 START WITH 1500
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
USE [MATERIAL]
GO
/****** Object:  Sequence [dbo].[MATTIP-00-MERCADORIA-REVENDA]    Script Date: 11/11/2015 15:44:44 ******/
CREATE SEQUENCE [dbo].[MATTIP-00-MERCADORIA-REVENDA] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
USE [MATERIAL]
GO
/****** Object:  Sequence [dbo].[MATTIP-01-MATERIA-PRIMA]    Script Date: 11/11/2015 15:44:44 ******/
CREATE SEQUENCE [dbo].[MATTIP-01-MATERIA-PRIMA] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
USE [MATERIAL]
GO
/****** Object:  Sequence [dbo].[MATTIP-02-EMBALAGEM]    Script Date: 11/11/2015 15:44:44 ******/
CREATE SEQUENCE [dbo].[MATTIP-02-EMBALAGEM] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
USE [MATERIAL]
GO
/****** Object:  Sequence [dbo].[MATTIP-03-PRODUTO-PROCESS]    Script Date: 11/11/2015 15:44:44 ******/
CREATE SEQUENCE [dbo].[MATTIP-03-PRODUTO-PROCESS] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
USE [MATERIAL]
GO
/****** Object:  Sequence [dbo].[MATTIP-04-PRODUTO-ACABADO]    Script Date: 11/11/2015 15:44:44 ******/
CREATE SEQUENCE [dbo].[MATTIP-04-PRODUTO-ACABADO] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
USE [MATERIAL]
GO
/****** Object:  Sequence [dbo].[MATTIP-05-SUBPRODUTO]    Script Date: 11/11/2015 15:44:44 ******/
CREATE SEQUENCE [dbo].[MATTIP-05-SUBPRODUTO] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
USE [MATERIAL]
GO
/****** Object:  Sequence [dbo].[MATTIP-06-PRODUTO-INTERMEDIARIO]    Script Date: 11/11/2015 15:44:44 ******/
CREATE SEQUENCE [dbo].[MATTIP-06-PRODUTO-INTERMEDIARIO] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
USE [MATERIAL]
GO
/****** Object:  Sequence [dbo].[MATTIP-07-USO-CONSUMO]    Script Date: 11/11/2015 15:44:44 ******/
CREATE SEQUENCE [dbo].[MATTIP-07-USO-CONSUMO] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
USE [MATERIAL]
GO
/****** Object:  Sequence [dbo].[MATTIP-08-ATIVO-IMOBILIZADO]    Script Date: 11/11/2015 15:44:44 ******/
CREATE SEQUENCE [dbo].[MATTIP-08-ATIVO-IMOBILIZADO] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
USE [MATERIAL]
GO
/****** Object:  Sequence [dbo].[MATTIP-09-SERVICOS]    Script Date: 11/11/2015 15:44:44 ******/
CREATE SEQUENCE [dbo].[MATTIP-09-SERVICOS] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
USE [MATERIAL]
GO
/****** Object:  Sequence [dbo].[MATTIP-10-INSUMOS]    Script Date: 11/11/2015 15:44:44 ******/
CREATE SEQUENCE [dbo].[MATTIP-10-INSUMOS] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
USE [MATERIAL]
GO
/****** Object:  Sequence [dbo].[MATTIP-99-OUTROS]    Script Date: 11/11/2015 15:44:44 ******/
CREATE SEQUENCE [dbo].[MATTIP-99-OUTROS] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
GO
/****** Object:  Table [dbo].[CONVERSAO_UNIDADE]    Script Date: 11/11/2015 15:44:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CONVERSAO_UNIDADE](
	[UNIDADE] [varchar](10) NOT NULL,
	[CONVERSAO] [varchar](10) NOT NULL,
	[FATOR] [numeric](18, 10) NOT NULL CONSTRAINT [DF_CONVERSAO_UNIDADE_FATOR]  DEFAULT ((1)),
 CONSTRAINT [PK_UNIDADE_CONVERSAO] PRIMARY KEY CLUSTERED 
(
	[UNIDADE] ASC,
	[CONVERSAO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[GRANDEZA]    Script Date: 11/11/2015 15:44:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[GRANDEZA](
	[GRANDEZA] [varchar](20) NOT NULL,
 CONSTRAINT [PK_GRANDEZA] PRIMARY KEY CLUSTERED 
(
	[GRANDEZA] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[IHM]    Script Date: 11/11/2015 15:44:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[IHM](
	[CODIGO] [varchar](20) NOT NULL,
	[SETOR] [varchar](20) NULL,
	[IP] [varchar](15) NULL,
	[MAQUINA] [varchar](20) NULL,
	[OPERADOR] [varchar](50) NULL,
 CONSTRAINT [PK_SUPERVISORIO] PRIMARY KEY CLUSTERED 
(
	[CODIGO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[IHM_LOG]    Script Date: 11/11/2015 15:44:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[IHM_LOG](
	[ID] [int] NOT NULL,
	[IHM] [varchar](20) NOT NULL,
	[DATAHORA] [datetime] NOT NULL,
	[MODO] [int] NOT NULL,
	[TEMPO] [int] NOT NULL CONSTRAINT [DF_IHM_LOG_TEMPO]  DEFAULT ((0)),
	[OPERADOR] [varchar](20) NULL,
	[SERIE] [int] NOT NULL,
 CONSTRAINT [PK_IHM_LOG] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[MAQUINA]    Script Date: 11/11/2015 15:44:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[MAQUINA](
	[CODIGO] [varchar](20) NOT NULL,
	[NOME] [varchar](50) NOT NULL,
	[SETOR] [varchar](20) NOT NULL,
	[SITUACAO] [int] NOT NULL CONSTRAINT [DF_MAQUINA_SITUACAO]  DEFAULT ((0)),
	[TEMPO] [int] NOT NULL CONSTRAINT [DF_MAQUINA_TEMPO]  DEFAULT ((0)),
	[OPERADOR] [varchar](50) NULL,
 CONSTRAINT [PK_MAQUINA] PRIMARY KEY CLUSTERED 
(
	[CODIGO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[MAQUINA_LOG]    Script Date: 11/11/2015 15:44:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[MAQUINA_LOG](
	[ID] [int] NOT NULL,
	[MAQUINA] [varchar](20) NOT NULL,
	[DATAHORA] [datetime] NOT NULL,
	[MODO] [int] NOT NULL,
	[TEMPO] [int] NOT NULL CONSTRAINT [DF_MAQUINA_LOG_TEMPO]  DEFAULT ((0)),
	[OPERADOR] [varchar](20) NULL,
	[SERIE] [int] NOT NULL,
 CONSTRAINT [PK_MAQUINA_LOG] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[MAQUINA_LOG_PARAMETRO]    Script Date: 11/11/2015 15:44:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[MAQUINA_LOG_PARAMETRO](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[MAQUINA_LOG] [int] NOT NULL,
	[MEDIDA] [varchar](20) NOT NULL,
	[UNIDADE] [varchar](10) NOT NULL,
	[VALOR] [numeric](18, 10) NOT NULL CONSTRAINT [DF_MAQUINA_LOG_MEDIDA_VALOR]  DEFAULT ((0)),
 CONSTRAINT [PK_MAQUINA_LOG_PARAMETRO] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[MATERIAL]    Script Date: 11/11/2015 15:44:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[MATERIAL](
	[CODIGO] [varchar](60) NOT NULL,
	[DESCRICAO] [varchar](100) NOT NULL,
	[TIPO] [char](2) NOT NULL,
	[ESTOQUE] [numeric](18, 10) NOT NULL CONSTRAINT [DF_MATERIAL_ESTOQUE]  DEFAULT ((0)),
 CONSTRAINT [PK_MATERIAL] PRIMARY KEY CLUSTERED 
(
	[CODIGO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[MATERIAL_COMPONENTE]    Script Date: 11/11/2015 15:44:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[MATERIAL_COMPONENTE](
	[MATERIAL] [varchar](60) NOT NULL,
	[COMPONENTE] [varchar](60) NOT NULL,
	[CONSUMO_MED] [varchar](20) NOT NULL,
	[CONSUMO_UN] [varchar](10) NOT NULL,
	[CONSUMO_VLR] [varchar](255) NOT NULL,
	[PERDA_MED] [varchar](20) NULL,
	[PERDA_UN] [varchar](10) NULL,
	[PERDA_VLR] [varchar](255) NULL,
 CONSTRAINT [PK_MATERIAL_COMPONENTE] PRIMARY KEY CLUSTERED 
(
	[MATERIAL] ASC,
	[COMPONENTE] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[MATERIAL_INVENTARIO]    Script Date: 11/11/2015 15:44:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[MATERIAL_INVENTARIO](
	[TIPO] [char](2) NOT NULL,
	[NUMERO] [numeric](18, 0) NOT NULL,
	[MATERIAL] [varchar](60) NOT NULL,
	[LOCAL] [varchar](50) NULL,
 CONSTRAINT [PK_MATERIAL_LOTE_1] PRIMARY KEY CLUSTERED 
(
	[TIPO] ASC,
	[NUMERO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[MATERIAL_INVENTARIO_MEDIDA]    Script Date: 11/11/2015 15:44:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[MATERIAL_INVENTARIO_MEDIDA](
	[TIPO] [char](2) NOT NULL,
	[NUMERO] [numeric](18, 0) NOT NULL CONSTRAINT [DF_MATERIAL_LOTE_MEDIDA_NUMERO]  DEFAULT ((0)),
	[MEDIDA] [varchar](20) NOT NULL,
	[UNIDADE] [varchar](10) NOT NULL,
	[VALOR] [numeric](18, 10) NOT NULL CONSTRAINT [DF_MATERIAL_LOTE_MEDIDA_VALOR]  DEFAULT ((0)),
 CONSTRAINT [PK_MATERIAL_LOTE_MEDIDA_1] PRIMARY KEY CLUSTERED 
(
	[TIPO] ASC,
	[NUMERO] ASC,
	[MEDIDA] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[MATERIAL_MEDIDA]    Script Date: 11/11/2015 15:44:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[MATERIAL_MEDIDA](
	[MATERIAL] [varchar](60) NOT NULL,
	[MEDIDA] [varchar](20) NOT NULL,
	[UNIDADE] [varchar](10) NOT NULL,
	[FORMULA] [varchar](255) NOT NULL,
 CONSTRAINT [PK_MATERIAL_ATR] PRIMARY KEY CLUSTERED 
(
	[MATERIAL] ASC,
	[MEDIDA] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[MATERIAL_MOVIMENTO]    Script Date: 11/11/2015 15:44:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[MATERIAL_MOVIMENTO](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[DATAHORA] [datetime] NOT NULL,
	[MAQUINA] [varchar](20) NULL,
	[OPERADOR] [varchar](20) NULL,
 CONSTRAINT [PK_MATERIAL_MOVEST] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[MATERIAL_MOVIMENTO_ITEM]    Script Date: 11/11/2015 15:44:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[MATERIAL_MOVIMENTO_ITEM](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[MOVIMENTO] [int] NOT NULL,
	[MOVIMENTO_TIPO] [char](2) NOT NULL,
	[LOTE_TIPO] [char](2) NOT NULL,
	[LOTE_NUMERO] [numeric](18, 0) NOT NULL CONSTRAINT [DF_EESTOQUE_MOVIMENTO_LOTE_NUMERO]  DEFAULT ((0)),
	[MATERIAL] [varchar](60) NOT NULL,
	[LOCAL] [varchar](50) NULL,
	[EM_USO] [bit] NOT NULL,
 CONSTRAINT [PK_ESTOQUE_MOVIMENTO] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[MATERIAL_MOVIMENTO_ITEM_MEDIDA]    Script Date: 11/11/2015 15:44:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[MATERIAL_MOVIMENTO_ITEM_MEDIDA](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[ITEM] [int] NOT NULL,
	[MEDIDA] [varchar](20) NOT NULL,
	[UNIDADE] [varchar](10) NOT NULL,
	[VALOR] [numeric](18, 10) NOT NULL CONSTRAINT [DF_MATERIAL_MOVIMENTO_ITEM_MEDIDA_VALOR]  DEFAULT ((0)),
 CONSTRAINT [PK_MATERIAL_MOVIMENTO_ITEM_MEDIDA] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[MATERIAL_MOVIMENTO_TIPO]    Script Date: 11/11/2015 15:44:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[MATERIAL_MOVIMENTO_TIPO](
	[CODIGO] [char](2) NOT NULL,
	[DESCRICAO] [varchar](100) NOT NULL,
	[OPERACAO] [char](1) NULL,
	[BALANCEADO] [bit] NOT NULL CONSTRAINT [DF_ESTOQUE_MOVIMENTO_TIPO_BALANCEADO]  DEFAULT ((0)),
	[ALTERA_SALDO_MATERIAL] [bit] NOT NULL CONSTRAINT [DF_MATERIAL_MOVIMENTO_TIPO_ALTERA_SALDO_MATERIAL]  DEFAULT ((0)),
	[ALTERA_SALDO_LOTE] [bit] NOT NULL CONSTRAINT [DF_MATERIAL_MOVIMENTO_TIPO_ALTERA_SALDO_LOTE]  DEFAULT ((0)),
	[GERAR_LOTE] [bit] NOT NULL CONSTRAINT [DF_MATERIAL_MOVIMENTO_TIPO_GERAR_LOTE]  DEFAULT ((0)),
 CONSTRAINT [PK_ESTOQUE_MOVIMENTO_TIPO] PRIMARY KEY CLUSTERED 
(
	[CODIGO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[MATERIAL_TIPO]    Script Date: 11/11/2015 15:44:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[MATERIAL_TIPO](
	[CODIGO] [char](2) NOT NULL,
	[DESCRICAO] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_TIPO_MATERIAL] PRIMARY KEY CLUSTERED 
(
	[CODIGO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[MEDIDA]    Script Date: 11/11/2015 15:44:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[MEDIDA](
	[NOME] [varchar](20) NOT NULL,
	[GRANDEZA] [varchar](20) NULL,
	[DESCRICAO] [varchar](50) NOT NULL,
	[VARIAVEL] [varchar](10) NULL,
	[UNIDADE_PADRAO] [varchar](10) NOT NULL,
 CONSTRAINT [PK_MEDIDA] PRIMARY KEY CLUSTERED 
(
	[NOME] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[OPERACAO]    Script Date: 11/11/2015 15:44:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[OPERACAO](
	[CODIGO] [varchar](10) NOT NULL,
	[DESCRICAO] [varchar](50) NOT NULL,
 CONSTRAINT [PK_OPE] PRIMARY KEY CLUSTERED 
(
	[CODIGO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[UNIDADE]    Script Date: 11/11/2015 15:44:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[UNIDADE](
	[SIMBOLO] [varchar](10) NOT NULL,
	[NOME] [varchar](50) NOT NULL,
	[GRANDEZA] [varchar](20) NOT NULL,
 CONSTRAINT [PK_UNIDADE] PRIMARY KEY CLUSTERED 
(
	[SIMBOLO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  View [dbo].[MAQUINA_LOG_STATUS_DAILY_SUMMARY]    Script Date: 11/11/2015 15:44:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


CREATE VIEW [dbo].[MAQUINA_LOG_STATUS_DAILY_SUMMARY] AS

SELECT CONVERT(date, DATAHORA) AS DATA, MAQUINA, MODO, MAX(TEMPO) AS TEMPO, SERIE
FROM MAQUINA_LOG
GROUP BY MAQUINA, CONVERT(date, DATAHORA), MODO, SERIE
--ORDER BY DATA, MAQUINA, SERIE





GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [IX_MAQUINA]    Script Date: 11/11/2015 15:44:44 ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_MAQUINA] ON [dbo].[MAQUINA]
(
	[NOME] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [IX_MATERIAL]    Script Date: 11/11/2015 15:44:44 ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_MATERIAL] ON [dbo].[MATERIAL]
(
	[DESCRICAO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [IX_MATERIAL_LOTE]    Script Date: 11/11/2015 15:44:44 ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_MATERIAL_LOTE] ON [dbo].[MATERIAL_INVENTARIO]
(
	[TIPO] ASC,
	[NUMERO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [IX_MATERIAL_LOTE_MEDIDA]    Script Date: 11/11/2015 15:44:44 ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_MATERIAL_LOTE_MEDIDA] ON [dbo].[MATERIAL_INVENTARIO_MEDIDA]
(
	[TIPO] ASC,
	[NUMERO] ASC,
	[MEDIDA] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [IX_TIPO_MATERIAL]    Script Date: 11/11/2015 15:44:44 ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_TIPO_MATERIAL] ON [dbo].[MATERIAL_TIPO]
(
	[DESCRICAO] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [IX_UNIDADE]    Script Date: 11/11/2015 15:44:44 ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_UNIDADE] ON [dbo].[UNIDADE]
(
	[NOME] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[CONVERSAO_UNIDADE]  WITH CHECK ADD  CONSTRAINT [FK_UNIDADE_CONVERSAO_UNIDADE] FOREIGN KEY([UNIDADE])
REFERENCES [dbo].[UNIDADE] ([SIMBOLO])
GO
ALTER TABLE [dbo].[CONVERSAO_UNIDADE] CHECK CONSTRAINT [FK_UNIDADE_CONVERSAO_UNIDADE]
GO
ALTER TABLE [dbo].[CONVERSAO_UNIDADE]  WITH CHECK ADD  CONSTRAINT [FK_UNIDADE_CONVERSAO_UNIDADE1] FOREIGN KEY([CONVERSAO])
REFERENCES [dbo].[UNIDADE] ([SIMBOLO])
GO
ALTER TABLE [dbo].[CONVERSAO_UNIDADE] CHECK CONSTRAINT [FK_UNIDADE_CONVERSAO_UNIDADE1]
GO
ALTER TABLE [dbo].[IHM_LOG]  WITH CHECK ADD  CONSTRAINT [FK_IHM_LOG_MAQUINA] FOREIGN KEY([IHM])
REFERENCES [dbo].[IHM] ([CODIGO])
GO
ALTER TABLE [dbo].[IHM_LOG] CHECK CONSTRAINT [FK_IHM_LOG_MAQUINA]
GO
ALTER TABLE [dbo].[MAQUINA_LOG]  WITH CHECK ADD  CONSTRAINT [FK_MAQUINA_LOG_MAQUINA] FOREIGN KEY([MAQUINA])
REFERENCES [dbo].[MAQUINA] ([CODIGO])
GO
ALTER TABLE [dbo].[MAQUINA_LOG] CHECK CONSTRAINT [FK_MAQUINA_LOG_MAQUINA]
GO
ALTER TABLE [dbo].[MAQUINA_LOG_PARAMETRO]  WITH CHECK ADD  CONSTRAINT [FK_MAQUINA_LOG_PARAMETRO_MAQUINA_LOG] FOREIGN KEY([MAQUINA_LOG])
REFERENCES [dbo].[MAQUINA_LOG] ([ID])
GO
ALTER TABLE [dbo].[MAQUINA_LOG_PARAMETRO] CHECK CONSTRAINT [FK_MAQUINA_LOG_PARAMETRO_MAQUINA_LOG]
GO
ALTER TABLE [dbo].[MATERIAL]  WITH CHECK ADD  CONSTRAINT [FK_MATERIAL_TIPO_MATERIAL] FOREIGN KEY([TIPO])
REFERENCES [dbo].[MATERIAL_TIPO] ([CODIGO])
GO
ALTER TABLE [dbo].[MATERIAL] CHECK CONSTRAINT [FK_MATERIAL_TIPO_MATERIAL]
GO
ALTER TABLE [dbo].[MATERIAL_COMPONENTE]  WITH CHECK ADD  CONSTRAINT [FK_MATERIAL_COMPONENTE_MATERIAL] FOREIGN KEY([MATERIAL])
REFERENCES [dbo].[MATERIAL] ([CODIGO])
GO
ALTER TABLE [dbo].[MATERIAL_COMPONENTE] CHECK CONSTRAINT [FK_MATERIAL_COMPONENTE_MATERIAL]
GO
ALTER TABLE [dbo].[MATERIAL_COMPONENTE]  WITH CHECK ADD  CONSTRAINT [FK_MATERIAL_COMPONENTE_MATERIAL1] FOREIGN KEY([COMPONENTE])
REFERENCES [dbo].[MATERIAL] ([CODIGO])
GO
ALTER TABLE [dbo].[MATERIAL_COMPONENTE] CHECK CONSTRAINT [FK_MATERIAL_COMPONENTE_MATERIAL1]
GO
ALTER TABLE [dbo].[MATERIAL_COMPONENTE]  WITH CHECK ADD  CONSTRAINT [FK_MATERIAL_COMPONENTE_MEDIDA] FOREIGN KEY([CONSUMO_MED])
REFERENCES [dbo].[MEDIDA] ([NOME])
GO
ALTER TABLE [dbo].[MATERIAL_COMPONENTE] CHECK CONSTRAINT [FK_MATERIAL_COMPONENTE_MEDIDA]
GO
ALTER TABLE [dbo].[MATERIAL_COMPONENTE]  WITH CHECK ADD  CONSTRAINT [FK_MATERIAL_COMPONENTE_MEDIDA1] FOREIGN KEY([PERDA_MED])
REFERENCES [dbo].[MEDIDA] ([NOME])
GO
ALTER TABLE [dbo].[MATERIAL_COMPONENTE] CHECK CONSTRAINT [FK_MATERIAL_COMPONENTE_MEDIDA1]
GO
ALTER TABLE [dbo].[MATERIAL_COMPONENTE]  WITH CHECK ADD  CONSTRAINT [FK_MATERIAL_COMPONENTE_UNIDADE] FOREIGN KEY([CONSUMO_UN])
REFERENCES [dbo].[UNIDADE] ([SIMBOLO])
GO
ALTER TABLE [dbo].[MATERIAL_COMPONENTE] CHECK CONSTRAINT [FK_MATERIAL_COMPONENTE_UNIDADE]
GO
ALTER TABLE [dbo].[MATERIAL_COMPONENTE]  WITH CHECK ADD  CONSTRAINT [FK_MATERIAL_COMPONENTE_UNIDADE1] FOREIGN KEY([PERDA_UN])
REFERENCES [dbo].[UNIDADE] ([SIMBOLO])
GO
ALTER TABLE [dbo].[MATERIAL_COMPONENTE] CHECK CONSTRAINT [FK_MATERIAL_COMPONENTE_UNIDADE1]
GO
ALTER TABLE [dbo].[MATERIAL_INVENTARIO]  WITH CHECK ADD  CONSTRAINT [FK_MATERIAL_LOTE_MATERIAL] FOREIGN KEY([MATERIAL])
REFERENCES [dbo].[MATERIAL] ([CODIGO])
GO
ALTER TABLE [dbo].[MATERIAL_INVENTARIO] CHECK CONSTRAINT [FK_MATERIAL_LOTE_MATERIAL]
GO
ALTER TABLE [dbo].[MATERIAL_INVENTARIO]  WITH CHECK ADD  CONSTRAINT [FK_MATERIAL_LOTE_MATERIAL_TIPO] FOREIGN KEY([TIPO])
REFERENCES [dbo].[MATERIAL_TIPO] ([CODIGO])
GO
ALTER TABLE [dbo].[MATERIAL_INVENTARIO] CHECK CONSTRAINT [FK_MATERIAL_LOTE_MATERIAL_TIPO]
GO
ALTER TABLE [dbo].[MATERIAL_INVENTARIO_MEDIDA]  WITH CHECK ADD  CONSTRAINT [FK_MATERIAL_LOTE_MEDIDA_MATERIAL_LOTE1] FOREIGN KEY([TIPO], [NUMERO])
REFERENCES [dbo].[MATERIAL_INVENTARIO] ([TIPO], [NUMERO])
GO
ALTER TABLE [dbo].[MATERIAL_INVENTARIO_MEDIDA] CHECK CONSTRAINT [FK_MATERIAL_LOTE_MEDIDA_MATERIAL_LOTE1]
GO
ALTER TABLE [dbo].[MATERIAL_INVENTARIO_MEDIDA]  WITH CHECK ADD  CONSTRAINT [FK_MATERIAL_LOTE_MEDIDA_MEDIDA] FOREIGN KEY([MEDIDA])
REFERENCES [dbo].[MEDIDA] ([NOME])
GO
ALTER TABLE [dbo].[MATERIAL_INVENTARIO_MEDIDA] CHECK CONSTRAINT [FK_MATERIAL_LOTE_MEDIDA_MEDIDA]
GO
ALTER TABLE [dbo].[MATERIAL_INVENTARIO_MEDIDA]  WITH CHECK ADD  CONSTRAINT [FK_MATERIAL_LOTE_MEDIDA_UNIDADE] FOREIGN KEY([UNIDADE])
REFERENCES [dbo].[UNIDADE] ([SIMBOLO])
GO
ALTER TABLE [dbo].[MATERIAL_INVENTARIO_MEDIDA] CHECK CONSTRAINT [FK_MATERIAL_LOTE_MEDIDA_UNIDADE]
GO
ALTER TABLE [dbo].[MATERIAL_MEDIDA]  WITH CHECK ADD  CONSTRAINT [FK_MATERIAL_ATR_MEDIDA] FOREIGN KEY([MEDIDA])
REFERENCES [dbo].[MEDIDA] ([NOME])
GO
ALTER TABLE [dbo].[MATERIAL_MEDIDA] CHECK CONSTRAINT [FK_MATERIAL_ATR_MEDIDA]
GO
ALTER TABLE [dbo].[MATERIAL_MEDIDA]  WITH CHECK ADD  CONSTRAINT [FK_MATERIAL_MEDIDA_MATERIAL] FOREIGN KEY([MATERIAL])
REFERENCES [dbo].[MATERIAL] ([CODIGO])
GO
ALTER TABLE [dbo].[MATERIAL_MEDIDA] CHECK CONSTRAINT [FK_MATERIAL_MEDIDA_MATERIAL]
GO
ALTER TABLE [dbo].[MATERIAL_MEDIDA]  WITH CHECK ADD  CONSTRAINT [FK_MATERIAL_MEDIDA_UNIDADE] FOREIGN KEY([UNIDADE])
REFERENCES [dbo].[UNIDADE] ([SIMBOLO])
GO
ALTER TABLE [dbo].[MATERIAL_MEDIDA] CHECK CONSTRAINT [FK_MATERIAL_MEDIDA_UNIDADE]
GO
ALTER TABLE [dbo].[MATERIAL_MOVIMENTO_ITEM]  WITH CHECK ADD  CONSTRAINT [FK_ESTOQUE_MOVIMENTO_ESTOQUE_MOVIMENTO_TIPO] FOREIGN KEY([MOVIMENTO_TIPO])
REFERENCES [dbo].[MATERIAL_MOVIMENTO_TIPO] ([CODIGO])
GO
ALTER TABLE [dbo].[MATERIAL_MOVIMENTO_ITEM] CHECK CONSTRAINT [FK_ESTOQUE_MOVIMENTO_ESTOQUE_MOVIMENTO_TIPO]
GO
ALTER TABLE [dbo].[MATERIAL_MOVIMENTO_ITEM]  WITH CHECK ADD  CONSTRAINT [FK_ESTOQUE_MOVIMENTO_ESTOQUE_TRANSACAO] FOREIGN KEY([MOVIMENTO])
REFERENCES [dbo].[MATERIAL_MOVIMENTO] ([ID])
GO
ALTER TABLE [dbo].[MATERIAL_MOVIMENTO_ITEM] CHECK CONSTRAINT [FK_ESTOQUE_MOVIMENTO_ESTOQUE_TRANSACAO]
GO
ALTER TABLE [dbo].[MATERIAL_MOVIMENTO_ITEM]  WITH CHECK ADD  CONSTRAINT [FK_ESTOQUE_MOVIMENTO_MATERIAL] FOREIGN KEY([MATERIAL])
REFERENCES [dbo].[MATERIAL] ([CODIGO])
GO
ALTER TABLE [dbo].[MATERIAL_MOVIMENTO_ITEM] CHECK CONSTRAINT [FK_ESTOQUE_MOVIMENTO_MATERIAL]
GO
ALTER TABLE [dbo].[MATERIAL_MOVIMENTO_ITEM]  WITH CHECK ADD  CONSTRAINT [FK_ESTOQUE_MOVIMENTO_MATERIAL_TIPO1] FOREIGN KEY([LOTE_TIPO])
REFERENCES [dbo].[MATERIAL_TIPO] ([CODIGO])
GO
ALTER TABLE [dbo].[MATERIAL_MOVIMENTO_ITEM] CHECK CONSTRAINT [FK_ESTOQUE_MOVIMENTO_MATERIAL_TIPO1]
GO
ALTER TABLE [dbo].[MATERIAL_MOVIMENTO_ITEM_MEDIDA]  WITH CHECK ADD  CONSTRAINT [FK_MATERIAL_MOVIMENTO_ITEM_MEDIDA_MATERIAL_MOVIMENTO_ITEM] FOREIGN KEY([ITEM])
REFERENCES [dbo].[MATERIAL_MOVIMENTO_ITEM] ([ID])
GO
ALTER TABLE [dbo].[MATERIAL_MOVIMENTO_ITEM_MEDIDA] CHECK CONSTRAINT [FK_MATERIAL_MOVIMENTO_ITEM_MEDIDA_MATERIAL_MOVIMENTO_ITEM]
GO
ALTER TABLE [dbo].[MATERIAL_MOVIMENTO_ITEM_MEDIDA]  WITH CHECK ADD  CONSTRAINT [FK_MATERIAL_MOVIMENTO_ITEM_MEDIDA_MEDIDA] FOREIGN KEY([MEDIDA])
REFERENCES [dbo].[MEDIDA] ([NOME])
GO
ALTER TABLE [dbo].[MATERIAL_MOVIMENTO_ITEM_MEDIDA] CHECK CONSTRAINT [FK_MATERIAL_MOVIMENTO_ITEM_MEDIDA_MEDIDA]
GO
ALTER TABLE [dbo].[MATERIAL_MOVIMENTO_ITEM_MEDIDA]  WITH CHECK ADD  CONSTRAINT [FK_MATERIAL_MOVIMENTO_ITEM_MEDIDA_UNIDADE] FOREIGN KEY([UNIDADE])
REFERENCES [dbo].[UNIDADE] ([SIMBOLO])
GO
ALTER TABLE [dbo].[MATERIAL_MOVIMENTO_ITEM_MEDIDA] CHECK CONSTRAINT [FK_MATERIAL_MOVIMENTO_ITEM_MEDIDA_UNIDADE]
GO
ALTER TABLE [dbo].[MEDIDA]  WITH CHECK ADD  CONSTRAINT [FK_MEDIDA_GRANDEZA] FOREIGN KEY([GRANDEZA])
REFERENCES [dbo].[GRANDEZA] ([GRANDEZA])
GO
ALTER TABLE [dbo].[MEDIDA] CHECK CONSTRAINT [FK_MEDIDA_GRANDEZA]
GO
ALTER TABLE [dbo].[MEDIDA]  WITH CHECK ADD  CONSTRAINT [FK_MEDIDA_UNIDADE] FOREIGN KEY([UNIDADE_PADRAO])
REFERENCES [dbo].[UNIDADE] ([SIMBOLO])
GO
ALTER TABLE [dbo].[MEDIDA] CHECK CONSTRAINT [FK_MEDIDA_UNIDADE]
GO
ALTER TABLE [dbo].[UNIDADE]  WITH CHECK ADD  CONSTRAINT [FK_UNIDADE_GRANDEZA] FOREIGN KEY([GRANDEZA])
REFERENCES [dbo].[GRANDEZA] ([GRANDEZA])
GO
ALTER TABLE [dbo].[UNIDADE] CHECK CONSTRAINT [FK_UNIDADE_GRANDEZA]
GO
USE [master]
GO
ALTER DATABASE [MATERIAL] SET  READ_WRITE 
GO
