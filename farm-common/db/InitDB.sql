-- Table: public."Contractor"

-- DROP TABLE public."Contractor";

CREATE TABLE public."Contractor"
(
  "Id" integer NOT NULL,
  "Name" character varying(250),
  "PhoneNumber" character varying(10), -- Номер телефона для уведомлений
  "EmailAddrs" character varying(100), -- Эл. адреса через ";" для уведомлений
  "Status" character varying(20) NOT NULL, -- Статус (активный, закрыт)
  "DateOpen" date NOT NULL, -- Дата начала работы с поставщиком
  "DateClose" date, -- Дата окончания работы с поставщиком
  "Comment" character varying(500),
  "NotificationType" smallint NOT NULL, -- Тип уведомления поставщика (1-смс, 2-емайл)
  CONSTRAINT "Contractor_PK_Id" PRIMARY KEY ("Id")
)
WITH (
OIDS=FALSE
);
ALTER TABLE public."Contractor"
OWNER TO postgres;
COMMENT ON COLUMN public."Contractor"."PhoneNumber" IS 'Номер телефона для уведомлений';
COMMENT ON COLUMN public."Contractor"."EmailAddrs" IS 'Эл. адреса через ";" для уведомлений';
COMMENT ON COLUMN public."Contractor"."Status" IS 'Статус (активный, закрыт)';
COMMENT ON COLUMN public."Contractor"."DateOpen" IS 'Дата начала работы с поставщиком';
COMMENT ON COLUMN public."Contractor"."DateClose" IS 'Дата окончания работы с поставщиком';
COMMENT ON COLUMN public."Contractor"."NotificationType" IS 'Тип уведомления поставщика (1-смс, 2-емайл)';


-- Index: public."Contractor_IDX_DateOpen"

-- DROP INDEX public."Contractor_IDX_DateOpen";

CREATE INDEX "Contractor_IDX_DateOpen"
ON public."Contractor"
USING btree
("DateOpen");

-- Index: public."Contractor_IDX_Name"

-- DROP INDEX public."Contractor_IDX_Name";

CREATE INDEX "Contractor_IDX_Name"
ON public."Contractor"
USING btree
("Name" COLLATE pg_catalog."default");

-- Index: public."Contractor_IDX_Status"

-- DROP INDEX public."Contractor_IDX_Status";

CREATE INDEX "Contractor_IDX_Status"
ON public."Contractor"
USING btree
("Status" COLLATE pg_catalog."default");



-- Table: public."Nomenclature"

-- DROP TABLE public."Nomenclature";

CREATE TABLE public."Nomenclature"
(
  "Id" integer NOT NULL,
  "Name" character varying(100), -- Наименование позиции
  "VolumeUnit" character varying(20), -- Единица объема (кг, литр)
  "ParsingNames" character varying(500), -- Наименование для парсинга в заявках (через ";"), регистронезависмо
  "Comment" character varying(500),
  CONSTRAINT "Nomenclature_PK_Id" PRIMARY KEY ("Id")
)
WITH (
OIDS=FALSE
);
ALTER TABLE public."Nomenclature"
OWNER TO postgres;
COMMENT ON COLUMN public."Nomenclature"."Name" IS 'Наименование позиции';
COMMENT ON COLUMN public."Nomenclature"."VolumeUnit" IS 'Единица объема (кг, литр)';
COMMENT ON COLUMN public."Nomenclature"."ParsingNames" IS 'Наименование для парсинга в заявках (через ";"), регистронезависмо';


-- Index: public."Nomenclature_IDX_Name"

-- DROP INDEX public."Nomenclature_IDX_Name";

CREATE INDEX "Nomenclature_IDX_Name"
ON public."Nomenclature"
USING btree
("Name" COLLATE pg_catalog."default");



-- Table: public."ContractorNomenclature"

-- DROP TABLE public."ContractorNomenclature";

CREATE TABLE public."ContractorNomenclature"
(
  "Id" integer NOT NULL,
  "ContractorId" integer NOT NULL,
  "NomenclatureId" integer NOT NULL,
  "Status" character varying(20) NOT NULL, -- Статус (активно, закрыто)
  "DateOpen" date NOT NULL, -- Дата начала использования номенклатуры
  "DateClose" date, -- Дата окончания использования номенклатуры
  "MinimalVolume" integer NOT NULL, -- Минимальный объем приема заказа в доставку
  "Comment" character varying(500),
  CONSTRAINT "ContractorNomenclature_PK_Id" PRIMARY KEY ("Id"),
  CONSTRAINT "ContractorNomenclature_FK_ContractorId" FOREIGN KEY ("ContractorId")
  REFERENCES public."Contractor" ("Id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "ContractorNomenclature_FK_NomenclatureId" FOREIGN KEY ("NomenclatureId")
  REFERENCES public."Nomenclature" ("Id") MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
OIDS=FALSE
);
ALTER TABLE public."ContractorNomenclature"
OWNER TO postgres;
COMMENT ON COLUMN public."ContractorNomenclature"."Status" IS 'Статус (активно, закрыто)';
COMMENT ON COLUMN public."ContractorNomenclature"."DateOpen" IS 'Дата начала использования номенклатуры';
COMMENT ON COLUMN public."ContractorNomenclature"."DateClose" IS 'Дата окончания использования номенклатуры';
COMMENT ON COLUMN public."ContractorNomenclature"."MinimalVolume" IS 'Минимальный объем приема заказа в доставку';


-- Index: public."ContractorNomenclature_IDX_ContractorId"

-- DROP INDEX public."ContractorNomenclature_IDX_ContractorId";

CREATE INDEX "ContractorNomenclature_IDX_ContractorId"
ON public."ContractorNomenclature"
USING btree
("ContractorId");

-- Index: public."ContractorNomenclature_IDX_NomenclatureId"

-- DROP INDEX public."ContractorNomenclature_IDX_NomenclatureId";

CREATE INDEX "ContractorNomenclature_IDX_NomenclatureId"
ON public."ContractorNomenclature"
USING btree
("NomenclatureId");

