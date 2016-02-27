-- Sequence: public.main_seq

-- DROP SEQUENCE public.main_seq;

CREATE SEQUENCE public.main_seq
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER TABLE public.main_seq
OWNER TO postgres;


-- Table: public.contractor

-- DROP TABLE public.contractor;

CREATE TABLE public.contractor
(
  id integer NOT NULL,
  contractor_type character varying(20), -- Тип контрагента: CONTRACTOR (поставщик), потом возможно CLIENT (заказчик)
  name character varying(250), -- Наименование клиента
  status character varying(20) NOT NULL, -- Статус (активный, закрыт)
  date_open date NOT NULL, -- Дата начала работы с поставщиком
  date_close date, -- Дата окончания работы с поставщиком
  comment character varying(500),
  notification_type smallint NOT NULL, -- Тип уведомления поставщика (1-смс, 2-емайл)
  CONSTRAINT contractor_pk_id PRIMARY KEY (id)
)
WITH (
OIDS=FALSE
);
ALTER TABLE public.contractor
OWNER TO postgres;
COMMENT ON COLUMN public.contractor.contractor_type IS 'Тип контрагента: CONTRACTOR (поставщик), потом возможно CLIENT (заказчик)';
COMMENT ON COLUMN public.contractor.name IS 'Наименование клиента';
COMMENT ON COLUMN public.contractor.status IS 'Статус (активный, закрыт)';
COMMENT ON COLUMN public.contractor.date_open IS 'Дата начала работы с поставщиком';
COMMENT ON COLUMN public.contractor.date_close IS 'Дата окончания работы с поставщиком';
COMMENT ON COLUMN public.contractor.notification_type IS 'Тип уведомления поставщика (1-смс, 2-емайл)';


-- Index: public.contractor_idx_date_open

-- DROP INDEX public.contractor_idx_date_open;

CREATE INDEX contractor_idx_date_open
ON public.contractor
USING btree
(date_open);

-- Index: public.contractor_idx_name

-- DROP INDEX public.contractor_idx_name;

CREATE INDEX contractor_idx_name
ON public.contractor
USING btree
(contractor_type COLLATE pg_catalog."default");

-- Index: public.contractor_idx_status

-- DROP INDEX public.contractor_idx_status;

CREATE INDEX contractor_idx_status
ON public.contractor
USING btree
(status COLLATE pg_catalog."default");



-- Table: public.nomenclature

-- DROP TABLE public.nomenclature;

CREATE TABLE public.nomenclature
(
  id integer NOT NULL,
  name character varying(100), -- Наименование позиции
  volume_unit character varying(20), -- Единица объема (кг, литр)
  parsing_names character varying(500), -- Наименование для парсинга в заявках (через ";"), регистронезависмо
  comment character varying(500),
  CONSTRAINT nomenclature_pk_id PRIMARY KEY (id)
)
WITH (
OIDS=FALSE
);
ALTER TABLE public.nomenclature
OWNER TO postgres;
COMMENT ON COLUMN public.nomenclature.name IS 'Наименование позиции';
COMMENT ON COLUMN public.nomenclature.volume_unit IS 'Единица объема (кг, литр)';
COMMENT ON COLUMN public.nomenclature.parsing_names IS 'Наименование для парсинга в заявках (через ";"), регистронезависмо';


-- Index: public.nomenclature_idx_name

-- DROP INDEX public.nomenclature_idx_name;

CREATE INDEX nomenclature_idx_name
ON public.nomenclature
USING btree
(name COLLATE pg_catalog."default");



-- Table: public.contractor_nomenclature

-- DROP TABLE public.contractor_nomenclature;

CREATE TABLE public.contractor_nomenclature
(
  id integer NOT NULL,
  contractor_id integer NOT NULL,
  nomenclature_id integer NOT NULL,
  status character varying(20) NOT NULL, -- Статус (активно, закрыто)
  date_open date NOT NULL, -- Дата начала использования номенклатуры
  date_close date, -- Дата окончания использования номенклатуры
  minimal_volume integer NOT NULL, -- Минимальный объем приема заказа в доставку
  comment character varying(500),
  CONSTRAINT contractor_nomenclature_pk_id PRIMARY KEY (id),
  CONSTRAINT contractor_nomenclature_fk_contractor_id FOREIGN KEY (contractor_id)
  REFERENCES public.contractor (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT contractor_nomenclature_fk_nomenclature_id FOREIGN KEY (nomenclature_id)
  REFERENCES public.nomenclature (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
OIDS=FALSE
);
ALTER TABLE public.contractor_nomenclature
OWNER TO postgres;
COMMENT ON COLUMN public.contractor_nomenclature.status IS 'Статус (активно, закрыто)';
COMMENT ON COLUMN public.contractor_nomenclature.date_open IS 'Дата начала использования номенклатуры';
COMMENT ON COLUMN public.contractor_nomenclature.date_close IS 'Дата окончания использования номенклатуры';
COMMENT ON COLUMN public.contractor_nomenclature.minimal_volume IS 'Минимальный объем приема заказа в доставку';


-- Index: public.contractor_nomenclature_idx_contractor_id

-- DROP INDEX public.contractor_nomenclature_idx_contractor_id;

CREATE INDEX contractor_nomenclature_idx_contractor_id
ON public.contractor_nomenclature
USING btree
(contractor_id);

-- Index: public.contractor_nomenclature_idx_nomenclature_id

-- DROP INDEX public.contractor_nomenclature_idx_nomenclature_id;

CREATE INDEX contractor_nomenclature_idx_nomenclature_id
ON public.contractor_nomenclature
USING btree
(nomenclature_id);


-- Table: public.contact

-- DROP TABLE public.contact;

CREATE TABLE public.contact
(
  id integer NOT NULL,
  contact_type smallint, -- Тип контакта: 1 - mob. phone, 2 - email
  contractor_id integer,
  contact character varying(50), -- Контакт
  status character varying(20), -- Статус: ACTIVE, INACTIVE
  CONSTRAINT contact_pk_id PRIMARY KEY (id),
  CONSTRAINT contact_fk_contractor_id FOREIGN KEY (contractor_id)
  REFERENCES public.contractor (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
OIDS=FALSE
);
ALTER TABLE public.contact
OWNER TO postgres;
COMMENT ON COLUMN public.contact.contact_type IS 'Тип контакта: 1 - mob. phone, 2 - email';
COMMENT ON COLUMN public.contact.contact IS 'Контакт';
COMMENT ON COLUMN public.contact.status IS 'Статус: ACTIVE, INACTIVE';


-- Index: public.contact_idx_contact_type

-- DROP INDEX public.contact_idx_contact_type;

CREATE INDEX contact_idx_contact_type
ON public.contact
USING btree
(contact_type, status COLLATE pg_catalog."default");

-- Index: public.contact_idx_contractor_id

-- DROP INDEX public.contact_idx_contractor_id;

CREATE INDEX contact_idx_contractor_id
ON public.contact
USING btree
(contractor_id);
