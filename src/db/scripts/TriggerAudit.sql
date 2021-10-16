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
        insert into AUDIT (id, type, date, ref_user, table_name)  
        values ((SELECT coalesce(MAX(id), 0) +1 FROM audit), TG_OP::TEXT, current_timestamp, (SELECT ref_user FROM login WHERE id=(SELECT max(id) FROM login)), TG_TABLE_NAME::TEXT);
        RETURN NEW;
    elsif (TG_OP = 'DELETE') then
        v_old_data := ROW(OLD.*);
        insert into AUDIT (id, type, date, ref_user, table_name)
        values ((SELECT coalesce(MAX(id), 0) +1 FROM audit), TG_OP::TEXT, current_timestamp, (SELECT ref_user FROM login WHERE id=(SELECT max(id) FROM login)), TG_TABLE_NAME::TEXT);
        RETURN OLD;
    elsif (TG_OP = 'INSERT') then
        v_new_data := ROW(NEW.*);      
        insert into AUDIT (id, type, date, ref_user, table_name)
        values ((SELECT coalesce(MAX(id), 0) +1 FROM audit), TG_OP::TEXT, current_timestamp, (SELECT ref_user FROM login WHERE id=(SELECT max(id) FROM login)), TG_TABLE_NAME::TEXT);
        RETURN NEW;
    else
        RAISE WARNING '[AUDIT.IF_MODIFIED_FUNC] - Other action occurred: %, at %',TG_OP,now();
        RETURN NULL;
    end if;
END;
$function$

CREATE trigger audit_log
AFTER INSERT OR UPDATE OR DELETE ON ARTISTS
FOR EACH ROW EXECUTE PROCEDURE audit_log();

CREATE trigger audit_log
AFTER INSERT OR UPDATE OR DELETE ON ATTRACTION
FOR EACH ROW EXECUTE PROCEDURE audit_log();

CREATE trigger audit_log
AFTER INSERT OR UPDATE OR DELETE ON OCCUPATION
FOR EACH ROW EXECUTE PROCEDURE audit_log();

CREATE trigger audit_log
AFTER INSERT OR UPDATE OR DELETE ON SHOWS
FOR EACH ROW EXECUTE PROCEDURE audit_log();

CREATE trigger audit_log
AFTER INSERT OR UPDATE OR DELETE ON TICKET_CONFIGS
FOR EACH ROW EXECUTE PROCEDURE audit_log();

CREATE trigger audit_log
AFTER INSERT OR UPDATE OR DELETE ON TICKET_SELLS
FOR EACH ROW EXECUTE PROCEDURE audit_log();

CREATE trigger audit_log
AFTER INSERT OR UPDATE OR DELETE ON USERS
FOR EACH ROW EXECUTE PROCEDURE audit_log();

-- DROP TRIGGER IF EXISTS audit_log ON audit