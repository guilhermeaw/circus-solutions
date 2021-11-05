package reports;

import com.itextpdf.text.Document;
import common.ReportToolkit;
import formatters.CpfFormatter;
import formatters.PhoneFormatter;
import entities.Artist;
import utils.ApplicationUtilities;

import java.util.List;

public class ArtistListReport extends ReportToolkit
{
    private List<Artist> artists;

    private final String[] header = new String[]
    {
            "Nome",
            "CPF",
            "Telefone",
            "Cargo"
    };

    private final float[] columnWidths = new float[]
    {
            15f,
            8f,
            8f,
            8f
    };

    public ArtistListReport( List<Artist> artists )
    {
        super( true );
        this.artists = artists;
    }

    @Override
    protected void generateDocument( Document document ) throws Exception
    {
        setTitle( "Relatório de Listagem de Artistas" );
        setSubtitle(ApplicationUtilities.getInstance().getCompany());

        separator();
        newLine();

        Table table = new Table(columnWidths);
        table.setHeader( header );

        for ( Artist artist : artists )
        {
            table.addRow(
                artist != null ? artist.getName() : "n/d",
                artist != null ? PhoneFormatter.format(artist.getPhone()) : "n/d",
                artist != null ? CpfFormatter.format(artist.getCpf()) : "n/d",
                artist != null ? artist.getOccupation().getName() : "n/d");
        }

        document.add( table );
    }
}
