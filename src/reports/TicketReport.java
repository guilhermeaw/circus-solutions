package reports;

import java.sql.Timestamp;
import java.util.Date;

import com.itextpdf.text.Document;
import common.ReportToolkit;
import entities.TicketSell;
import formatters.CurrencyFormatter;
import formatters.DateFormatter;
import utils.ApplicationUtilities;

public class TicketReport extends ReportToolkit
{
    private TicketSell ticketSell;

    public TicketReport( TicketSell ticketSell )
    {
        super( false );
        this.ticketSell = ticketSell;
    }

    @Override
    protected void generateDocument( Document document ) throws Exception
    {
        setTitle( "Ingresso - " + ticketSell.getSequential() );
        setSubtitle(ApplicationUtilities.getInstance().getCompany());

        separator();
        newLine();

        DetailsTable details = new DetailsTable( 4f, 10f );
        details.addRow("Ingresso", String.valueOf(ticketSell.getSequential()));
        details.addRow("Espetáculo", ticketSell.getShow().toString());
        details.addRow("Valor", CurrencyFormatter.format(String.valueOf(ticketSell.getTicketConfig().getValue())));
        details.addRow("Data da venda", DateFormatter.format(new Timestamp(new Date().getTime())));

        document.add( details );
    }
}
