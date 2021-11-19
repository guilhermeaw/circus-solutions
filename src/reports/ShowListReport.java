package reports;

import com.itextpdf.text.Document;
import common.ReportToolkit;
import entities.Show;
import utils.ApplicationUtilities;

import java.util.List;

public class ShowListReport extends ReportToolkit
{
    private List<Show> shows;

    private final String[] header = new String[]
    {
            "Capacidade",
            "Cidade",
            "Data"
    };

    private final float[] columnWidths = new float[]
    {
            8f,
            15f,
            8f
    };

    public ShowListReport( List<Show> shows )
    {
        super( true );
        this.shows = shows;
    }

    @Override
    protected void generateDocument( Document document ) throws Exception
    {
        setTitle( "Relatório de Listagem de Espetáculos" );
        setSubtitle(ApplicationUtilities.getInstance().getCompany());

        separator();
        newLine();

        Table table = new Table(columnWidths);
        table.setHeader( header );

        for ( Show show : shows )
        {
            table.addRow(
                show != null ? show.getCapacity() : "n/d",
                show != null ? show.getCity() : "n/d",
                show != null ? show.getDate() : "n/d");
        }

        document.add( table );
    }
}
