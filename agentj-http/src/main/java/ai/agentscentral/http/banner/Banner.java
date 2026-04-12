package ai.agentscentral.http.banner;

/**
 * Utility class that prints the AgentJ ASCII-art startup banner to standard output.
 *
 * <p>Called once by {@link ai.agentscentral.http.runner.AgentJStarter#run} before the
 * HTTP server starts.  This is a utility class and cannot be instantiated.</p>
 *
 * @author Rizwan Idrees
 */
public class Banner {

    private Banner() {
    }

    private final static String banner = """
  
  
         █████╗  ██████╗ ███████╗███╗   ██╗████████╗  ██╗
        ██╔══██╗██╔════╝ ██╔════╝████╗  ██║╚══██╔══╝  ██║
        ███████║██║  ███╗█████╗  ██╔██╗ ██║   ██║     ██║
        ██╔══██║██║   ██║██╔══╝  ██║╚██╗██║   ██║██   ██║
        ██║  ██║╚██████╔╝███████╗██║ ╚████║   ██║╚█████╔╝
        ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚═╝  ╚═══╝   ╚═╝ ╚════╝\s
                                                        \s
                                                                 
""";


    /**
     * Prints the AgentJ startup banner to {@link System#out}.
     */
    public static void printBanner(){
        System.out.println(banner);
    }

}
