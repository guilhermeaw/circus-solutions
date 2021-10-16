CREATE OR REPLACE FUNCTION public.audit_log()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
DECLARE
    v_old_data TEXT;
    v_new_data TEXT;
BEGIN
    if (TG_OP = 'UPDATE') then
        v_old_data := ROW(OLD.*);
        v_new_data := ROW(NEW.*);
        insert into AUDIT (ID, TYPE, DATA, REF_USER)   
        values (DEFAULT, TG_OP::TEXT, current_timestamp, (SELECT * FROM login WHERE id=(SELECT max(id) FROM login)));
        RETURN NEW;
    elsif (TG_OP = 'DELETE') then
        v_old_data := ROW(OLD.*);
        insert into AUDIT (ID, TYPE, DATA, REF_USER)  
        values (DEFAULT, TG_OP::TEXT, current_timestamp, (SELECT * FROM login WHERE id=(SELECT max(id) FROM login)));
        RETURN OLD;
    elsif (TG_OP = 'INSERT') then
        v_new_data := ROW(NEW.*);      
        insert into AUDIT (ID, TYPE, DATA, REF_USER)   
        values (DEFAULT, TG_OP::TEXT, current_timestamp, (SELECT * FROM login WHERE id=(SELECT max(id) FROM login)));
        RETURN NEW;
    else
        RAISE WARNING '[AUDIT.IF_MODIFIED_FUNC] - Other action occurred: %, at %',TG_OP,now();
        RETURN NULL;
    end if;
END;
$function$
