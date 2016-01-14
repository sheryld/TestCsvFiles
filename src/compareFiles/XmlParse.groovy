package compareFiles

import groovy.util.slurpersupport.GPathResult


import java.nio.file.Path
import java.nio.file.Paths


//---------- Main ---------------
//


/*def finder  = new Find()


def ret=new XmlFinder().getFilesNames("/Users/kuali/Documents/sqlWithParmFiles.xml")
ret.each {fname->
	finder.findFullPathFile("/Volumes/KFS/Projects_Kuali/sql-reports/kfs/",fname)
}*/



public class XmlFinder {


	/**
	 * Reads the files xml and sets up the file with parmsto be run by the jar files.
	 */
	public List getFilesNames(String f,List filenameswparms) {
		try {
			def find  = new Find()
			System.out.println "hello there \n"
			def fff = ""
			def xmlFile = SqlTOCSVTestAllFiles.getResourceAsStream(f)

			GPathResult sqls = new XmlSlurper().parse(xmlFile)
			List filenames = new ArrayList()


			def files = sqls.FILES.breadthFirst().findAll { node ->

                node.name() == 'file'
            }.each { fx ->

				String fname = fx.@name.text()

                def fullfname = find.findFullPathFile("/Volumes/KFS/Projects_Kuali/sql-reports/kfs/", fname)
                filenames?.add(fname)
				def pars = fx.parms.children().each { parmer ->
                    if (parmer.text() != null) {
                        fullfname = fullfname.toString() + parmer.text()

                        print parmer.text()
                    }

				}
			fff =	fullfname.replace("\n", " ")
				filenameswparms.add(fff)
			}
			return filenames
			


		} catch (Exception e) {
			System.err.println("Error parsing rule xml file. Please check file. : ", e);
			System.exit(1);
		}
	}

	

}
