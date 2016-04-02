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

-- Table: public.subject

-- DROP TABLE public.subject;

CREATE TABLE public.subject
(
  id integer NOT NULL,
  subject_type smallint, -- Тип субъекта: 1 - client, 2 - contractor, 3 - operator
  name character varying(250), -- Наименование клиента
  status character varying(20) NOT NULL, -- Статус: ACTIVE, CLOSED
  date_open date NOT NULL, -- Дата начала работы с поставщиком
  date_close date, -- Дата окончания работы с поставщиком
  comment character varying(500),
  CONSTRAINT subject_pk_id PRIMARY KEY (id)
)
WITH (
OIDS=FALSE
);
ALTER TABLE public.subject
OWNER TO postgres;
COMMENT ON TABLE public.subject
IS 'Базовый субъект для клиентов, поставщиков, операторов и др.';
COMMENT ON COLUMN public.subject.subject_type IS 'Тип субъекта: 1 - client, 2 - contractor, 3 - operator';
COMMENT ON COLUMN public.subject.name IS 'Наименование клиента';
COMMENT ON COLUMN public.subject.status IS 'Статус: ACTIVE, CLOSED';
COMMENT ON COLUMN public.subject.date_open IS 'Дата начала работы с поставщиком';
COMMENT ON COLUMN public.subject.date_close IS 'Дата окончания работы с поставщиком';


-- Index: public.subject_idx_date_open

-- DROP INDEX public.subject_idx_date_open;

CREATE INDEX subject_idx_date_open
ON public.subject
USING btree
(date_open);

-- Index: public.subject_idx_status

-- DROP INDEX public.subject_idx_status;

CREATE INDEX subject_idx_status
ON public.subject
USING btree
(status COLLATE pg_catalog."default");

-- Index: public.subject_idx_type

-- DROP INDEX public.subject_idx_type;

CREATE INDEX subject_idx_type
ON public.subject
USING btree
(subject_type);



-- Table: public.client

-- DROP TABLE public.client;

CREATE TABLE public.client
(
  id integer NOT NULL,
  delivery_address character varying(500), -- Адрес доставки
  CONSTRAINT client_pk_id PRIMARY KEY (id),
  CONSTRAINT client_fk_id FOREIGN KEY (id)
  REFERENCES public.subject (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
OIDS=FALSE
);
ALTER TABLE public.client
OWNER TO postgres;
COMMENT ON TABLE public.client
IS 'Клиенты';
COMMENT ON COLUMN public.client.delivery_address IS 'Адрес доставки';



-- Table: public.contractor

-- DROP TABLE public.contractor;

CREATE TABLE public.contractor
(
  id integer NOT NULL,
  CONSTRAINT contractor_pk_id PRIMARY KEY (id),
  CONSTRAINT contractor_fk_id FOREIGN KEY (id)
  REFERENCES public.subject (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
OIDS=FALSE
);
ALTER TABLE public.contractor
OWNER TO postgres;
COMMENT ON TABLE public.contractor
IS 'Поставщики';


-- Table: public.operator

-- DROP TABLE public.operator;

CREATE TABLE public.operator
(
  id integer NOT NULL,
  CONSTRAINT operator_pk_id PRIMARY KEY (id),
  CONSTRAINT operator_fk_id FOREIGN KEY (id)
  REFERENCES public.subject (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
OIDS=FALSE
);
ALTER TABLE public.operator
OWNER TO postgres;
COMMENT ON TABLE public.operator
IS 'Оператор';


-- Table: public.contact

-- DROP TABLE public.contact;

CREATE TABLE public.contact
(
  id integer NOT NULL,
  contact_type smallint NOT NULL, -- Тип контакта: 1 - mob. phone, 2 - email
  subject_id integer,
  contact character varying(50) NOT NULL, -- Контакт
  status character varying(20) NOT NULL, -- Статус: ACTIVE, INACTIVE
  CONSTRAINT contact_pk_id PRIMARY KEY (id),
  CONSTRAINT contact_fk_subject_id FOREIGN KEY (subject_id)
  REFERENCES public.subject (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
OIDS=FALSE
);
ALTER TABLE public.contact
OWNER TO postgres;
COMMENT ON TABLE public.contact
IS 'Контакты';
COMMENT ON COLUMN public.contact.contact_type IS 'Тип контакта: 1 - mob. phone, 2 - email';
COMMENT ON COLUMN public.contact.contact IS 'Контакт';
COMMENT ON COLUMN public.contact.status IS 'Статус: ACTIVE, INACTIVE';


-- Index: public.contact_idx_contact_type

-- DROP INDEX public.contact_idx_contact_type;

CREATE INDEX contact_idx_contact_type
ON public.contact
USING btree
(contact_type, status COLLATE pg_catalog."default");

-- Index: public.contact_idx_subject_id

-- DROP INDEX public.contact_idx_subject_id;

CREATE INDEX contact_idx_subject_id
ON public.contact
USING btree
(subject_id);



-- Table: public.nomenclature

-- DROP TABLE public.nomenclature;

CREATE TABLE public.nomenclature
(
  id integer NOT NULL,
  name character varying(100) NOT NULL, -- Наименование позиции
  volume_unit character varying(20) NOT NULL, -- Единица объема (кг, литр)
  parsing_names character varying(500), -- Наименование для парсинга в заявках (через ";"), регистронезависмо
  comment character varying(500),
  CONSTRAINT nomenclature_pk_id PRIMARY KEY (id)
)
WITH (
OIDS=FALSE
);
ALTER TABLE public.nomenclature
OWNER TO postgres;
COMMENT ON TABLE public.nomenclature
IS 'Номенклатура';
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
  status character varying(20) NOT NULL, -- Статус: ACTIVE, CLOSED
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
COMMENT ON TABLE public.contractor_nomenclature
IS 'Номенклатуры поставщиков';
COMMENT ON COLUMN public.contractor_nomenclature.status IS 'Статус: ACTIVE, CLOSED';
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



-- Table: public.orders

-- DROP TABLE public.orders;

CREATE TABLE public.orders
(
  id integer NOT NULL,
  origin smallint NOT NULL, -- Происхождение заказа: 1 - смс, 2 - сайт
  order_num character varying(30) NOT NULL, -- Номер заявки (для передачи клиенту/поставщику)
  order_dt timestamp without time zone NOT NULL, -- Дата и время получения заказа
  nomenclature_id integer NOT NULL,
  volume integer NOT NULL, -- Объем заказа
  delivery_address character varying(500) NOT NULL, -- Адрес доставки
  status character varying(20) NOT NULL, -- Статус заказа: NEW (новый)
  comment character varying(500),
  contractor_id integer, -- Поставщик, который доставляет заказ
  CONSTRAINT orders_pk_id PRIMARY KEY (id),
  CONSTRAINT orders_fk_contractor_id FOREIGN KEY (contractor_id)
  REFERENCES public.contractor (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT orders_fk_nomenclature_id FOREIGN KEY (nomenclature_id)
  REFERENCES public.nomenclature (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
OIDS=FALSE
);
ALTER TABLE public.orders
OWNER TO postgres;
COMMENT ON TABLE public.orders
IS 'Заказы';
COMMENT ON COLUMN public.orders.origin IS 'Происхождение заказа: 1 - смс, 2 - сайт';
COMMENT ON COLUMN public.orders.order_num IS 'Номер заявки (для передачи клиенту/поставщику)';
COMMENT ON COLUMN public.orders.order_dt IS 'Дата и время получения заказа';
COMMENT ON COLUMN public.orders.volume IS 'Объем заказа';
COMMENT ON COLUMN public.orders.delivery_address IS 'Адрес доставки';
COMMENT ON COLUMN public.orders.status IS 'Статус заказа: NEW (новый)';
COMMENT ON COLUMN public.orders.contractor_id IS 'Поставщик, который доставляет заказ';


-- Index: public.orders_idx_contractor_id

-- DROP INDEX public.orders_idx_contractor_id;

CREATE INDEX orders_idx_contractor_id
ON public.orders
USING btree
(contractor_id);

-- Index: public.orders_idx_nomenclature_id

-- DROP INDEX public.orders_idx_nomenclature_id;

CREATE INDEX orders_idx_nomenclature_id
ON public.orders
USING btree
(nomenclature_id);

-- Index: public.orders_idx_order_dt

-- DROP INDEX public.orders_idx_order_dt;

CREATE INDEX orders_idx_order_dt
ON public.orders
USING btree
(order_dt, status COLLATE pg_catalog."default");

-- Index: public.orders_idx_order_num

-- DROP INDEX public.orders_idx_order_num;

CREATE INDEX orders_idx_order_num
ON public.orders
USING btree
(order_num COLLATE pg_catalog."default", status COLLATE pg_catalog."default");



-- Table: public.order_history

-- DROP TABLE public.order_history;

CREATE TABLE public.order_history
(
  id integer NOT NULL,
  order_id integer NOT NULL,
  dt timestamp without time zone NOT NULL, -- Дата изменения заказа
  origin smallint NOT NULL, -- Источник изменения статуса
  status character varying(20) NOT NULL, -- Статус заказа
  CONSTRAINT order_history_pk_id PRIMARY KEY (id),
  CONSTRAINT order_history_fk_order_id FOREIGN KEY (order_id)
  REFERENCES public.orders (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
OIDS=FALSE
);
ALTER TABLE public.order_history
OWNER TO postgres;
COMMENT ON TABLE public.order_history
IS 'История изменения статуса заказа';
COMMENT ON COLUMN public.order_history.dt IS 'Дата изменения заказа';
COMMENT ON COLUMN public.order_history.origin IS 'Источник изменения статуса';
COMMENT ON COLUMN public.order_history.status IS 'Статус заказа';


-- Index: public.order_history_idx_dt

-- DROP INDEX public.order_history_idx_dt;

CREATE INDEX order_history_idx_dt
ON public.order_history
USING btree
(dt, status COLLATE pg_catalog."default", origin);

-- Index: public.order_history_idx_order_id

-- DROP INDEX public.order_history_idx_order_id;

CREATE INDEX order_history_idx_order_id
ON public.order_history
USING btree
(order_id);

-- Index: public.order_history_idx_status

-- DROP INDEX public.order_history_idx_status;

CREATE INDEX order_history_idx_status
ON public.order_history
USING btree
(status COLLATE pg_catalog."default");



-- Table: public.contractor_notification

-- DROP TABLE public.contractor_notification;

CREATE TABLE public.contractor_notification
(
  id integer NOT NULL,
  CONSTRAINT contractor_notification_pk_id PRIMARY KEY (id)
)
WITH (
OIDS=FALSE
);
ALTER TABLE public.contractor_notification
OWNER TO postgres;
COMMENT ON TABLE public.contractor_notification
IS 'Уведомления поставщиков';


-- Table: public.order_notification

-- DROP TABLE public.order_notification;

CREATE TABLE public.order_notification
(
  id integer NOT NULL,
  CONSTRAINT order_notification_pk_id PRIMARY KEY (id)
)
WITH (
OIDS=FALSE
);
ALTER TABLE public.order_notification
OWNER TO postgres;
COMMENT ON TABLE public.order_notification
IS 'Уведомления по заказам';


-- Table: public.notification

-- DROP TABLE public.notification;

CREATE TABLE public.notification
(
  id integer NOT NULL,
  CONSTRAINT notification_pk_id PRIMARY KEY (id)
)
WITH (
OIDS=FALSE
);
ALTER TABLE public.notification
OWNER TO postgres;
COMMENT ON TABLE public.notification
IS 'Исходящие уведомления';


-- Table: public."user"

-- DROP TABLE public."user";

CREATE TABLE public."user"
(
  id integer NOT NULL,
  name character varying(60) NOT NULL,
  password character varying(60) NOT NULL,
  CONSTRAINT user_pk_id PRIMARY KEY (id)
)
WITH (
OIDS=FALSE
);
ALTER TABLE public."user"
OWNER TO postgres;
COMMENT ON TABLE public."user"
IS 'Пользователи';


-- Table: public.requisition_site

-- DROP TABLE public.requisition_site;

CREATE TABLE public.requisition_site
(
  id integer NOT NULL,
  CONSTRAINT requisition_site_pk_id PRIMARY KEY (id)
)
WITH (
OIDS=FALSE
);
ALTER TABLE public.requisition_site
OWNER TO postgres;
COMMENT ON TABLE public.requisition_site
IS 'Заявки с сайта';


-- Table: public.requisition_sms

-- DROP TABLE public.requisition_sms;

CREATE TABLE public.requisition_sms
(
  id integer NOT NULL,
  CONSTRAINT requisition_sms_pk_id PRIMARY KEY (id)
)
WITH (
OIDS=FALSE
);
ALTER TABLE public.requisition_sms
OWNER TO postgres;
COMMENT ON TABLE public.requisition_sms
IS 'Заявки по смс';


