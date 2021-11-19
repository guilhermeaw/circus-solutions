package reports;

import com.itextpdf.text.Document;
import common.ReportToolkit;
import entities.TicketConfig;
import utils.ApplicationUtilities;

import java.util.List;

public class TicketListReport extends ReportToolkit
{
    private List<TicketConfig> tickets;

    private final String[] header = new String[]
    {
            "Autor",
            "Data de Criação",
            "Valor"
    };

    private final float[] columnWidths = new float[]
    {
            15f,
            8f,
            8f
    };

    public TicketListReport( List<TicketConfig> tickets )
    {
        super( true );
        this.tickets = tickets;
    }

    @Override
    protected void generateDocument( Document document ) throws Exception
    {
        setTitle( "Relatório de Listagem de Ingressos" );
        setSubtitle(ApplicationUtilities.getInstance().getCompany());

        separator();
        newLine();

        Table table = new Table(columnWidths);
        table.setHeader( header );

        for ( TicketConfig tickets : tickets )
        {
            table.addRow(
                tickets != null ? tickets.getCreatedDate() : "n/d",
                tickets != null ? tickets.getValue() : "n/d");
        }

        document.add( table );
    }
}
