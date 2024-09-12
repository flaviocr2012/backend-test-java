ALTER TABLE public.vehicle ADD COLUMN company_id int8;

ALTER TABLE public.vehicle ADD CONSTRAINT vehicle_company_fk FOREIGN KEY (company_id) REFERENCES public.company(id);