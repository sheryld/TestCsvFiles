package compareFiles

import groovy.util.slurpersupport.GPathResult
import java.beans.XMLDecoder
import java.io.StringReader
import java.io.StringWriter
import java.nio.file.Path
import java.nio.file.Paths
import java.sql.PreparedStatement
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;



import java.sql.SQLException
import org.apache.log4j.*

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

//---------- Main ---------------
//  parse command line options


def finder  = new Find()


def ret=new XmlFinder().getFilesNames("/Users/kuali/Documents/sqlWithParmFiles.xml")
ret.each {fname->
	finder.findFullPathFile("/Volumes/KFS/Projects_Kuali/sql-reports/kfs/",fname)
}

/**
 * Used to upgrade the maintenance document xml stored in
 * krns_maint_doc_t.doc_cntnt to be able to still open and use any maintenance
 * documents that were enroute at the time of an upgrade to Rice 2.0.
 *
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */

public class XmlFinder {

	private List classNameRuleMap;
	private List packageNameRuleMap;
	private List maintImplRuleMap;
	private HashMap<String, HashMap<String, String>> classPropertyRuleMap;

	

	/**
	 * Upgrades the xml using the rule maps executing the following actions :
	 * 1. Replace class names from rules. 2. Upgrade BO notes 3. Update class property names from rules
	 * 4. Set MaintainableImplClass name from rules.
	 *
	 * @param newXml - the old xml that must be upgraded
	 * @return the upgraded xml string
	 * @throws Exception
	 */
	public String upgradeXML(String oldXml) throws Exception {
		String newXml = oldXml;
		groovy.util.Node test
		InputStream  test3
		// Replace class names groovy.util.Node' to class 'org.w3c.dom.Node'
		def maintainableDocumentContents = new XmlParser().parse(new InputSource(new ByteArrayInputStream(oldXml.getBytes("utf-8"))))
		classNameRuleMap?.each {
			def sss = it.tokenize( ':' )

			//newXml = newXml.replaceAll(sss[0], sss[1])
			def olds  = sss[0]
			def news = sss[1]
			
		test = 	maintainableDocumentContents.oldMaintainableObject."*".find{node->
			node.name() == olds
			
		}
		def test2 = "org.kuali.kfs.coa.businessobject.Account[attributes={}; value=[chartOfAccountsCode[attributes={}; value=[HA]], accountNumber[attributes={}; value=[3028201]], accountName[attributes={}; value=[RTRF CC, HAWAII]], accountsFringesBnftIndicator[attributes={}; value=[true]], accountCityName[attributes={}; value=[Hilo]], accountStateCode[attributes={}; value=[HI]], accountStreetAddress[attributes={}; value=[200 West Kawili St]], accountZipCode[attributes={}; value=[96720]], accountCreateDate[attributes={}; value=[1998-07-16]], accountEffectiveDate[attributes={}; value=[1998-07-16]], accountInFinancialProcessingIndicator[attributes={}; value=[false]], budgetRecordingLevelCode[attributes={}; value=[A]], accountSufficientFundsCode[attributes={}; value=[N]], pendingAcctSufficientFundsIndicator[attributes={}; value=[false]], extrnlFinEncumSufficntFndIndicator[attributes={}; value=[false]], intrnlFinEncumSufficntFndIndicator[attributes={}; value=[false]], finPreencumSufficientFundIndicator[attributes={}; value=[false]], financialObjectivePrsctrlIndicator[attributes={}; value=[false]], accountOffCampusIndicator[attributes={}; value=[false]], active[attributes={}; value=[true]], accountFiscalOfficerSystemIdentifier[attributes={}; value=[14559]], accountsSupervisorySystemsIdentifier[attributes={}; value=[13997]], accountManagerSystemIdentifier[attributes={}; value=[13168]], organizationCode[attributes={}; value=[ADAF]], accountTypeCode[attributes={}; value=[NA]], accountPhysicalCampusCode[attributes={}; value=[HA]], subFundGroupCode[attributes={}; value=[RTRF]], financialHigherEdFunctionCd[attributes={}; value=[BIRC]], accountRestrictedStatusCode[attributes={}; value=[U]], continuationFinChrtOfAcctCd[attributes={}; value=[HA]], continuationAccountNumber[attributes={}; value=[3029032]], accountFiscalOfficerUser[attributes={class=org.kuali.rice.kim.bo.impl.PersonImpl}; value=[principalId[attributes={}; value=[14559]], principalName[attributes={}; value=[shorimot]], entityId[attributes={}; value=[10365369]], entityTypeCode[attributes={}; value=[PERSON]], firstName[attributes={}; value=[Susan]], middleName[attributes={}; value=[]], lastName[attributes={}; value=[Horimoto]], name[attributes={}; value=[Horimoto, Susan]], addressLine1[attributes={}; value=[PO BOX 323]], addressLine2[attributes={}; value=[]], addressLine3[attributes={}; value=[]], addressCityName[attributes={}; value=[PAPAIKOU]], addressStateCode[attributes={}; value=[HI]], addressPostalCode[attributes={}; value=[96781]], addressCountryCode[attributes={}; value=[US]], emailAddress[attributes={}; value=[shorimot@hawaii.edu]], phoneNumber[attributes={}; value=[808-934-2743]], suppressName[attributes={}; value=[false]], suppressAddress[attributes={}; value=[false]], suppressPhone[attributes={}; value=[false]], suppressPersonal[attributes={}; value=[false]], suppressEmail[attributes={}; value=[false]], campusCode[attributes={}; value=[HA]], externalIdentifiers[attributes={}; value=[]], employeeStatusCode[attributes={}; value=[A]], employeeTypeCode[attributes={}; value=[P]], primaryDepartmentCode[attributes={}; value=[HA-HA]], employeeId[attributes={}; value=[10365369]], baseSalaryAmount[attributes={}; value=[value[attributes={}; value=[0.00]]]], active[attributes={}; value=[true]]]], accountSupervisoryUser[attributes={class=org.kuali.rice.kim.bo.impl.PersonImpl}; value=[principalId[attributes={}; value=[13997]], principalName[attributes={}; value=[jamesyos]], entityId[attributes={}; value=[10329215]], entityTypeCode[attributes={}; value=[PERSON]], firstName[attributes={}; value=[James]], middleName[attributes={}; value=[]], lastName[attributes={}; value=[Yoshida]], name[attributes={}; value=[Yoshida, James]], addressLine1[attributes={}; value=[P O BOX 5377]], addressLine2[attributes={}; value=[]], addressLine3[attributes={}; value=[]], addressCityName[attributes={}; value=[HILO]], addressStateCode[attributes={}; value=[HI]], addressPostalCode[attributes={}; value=[96720]], addressCountryCode[attributes={}; value=[US]], emailAddress[attributes={}; value=[jamesyos@hawaii.edu]], phoneNumber[attributes={}; value=[808-934-2508]], suppressName[attributes={}; value=[false]], suppressAddress[attributes={}; value=[false]], suppressPhone[attributes={}; value=[false]], suppressPersonal[attributes={}; value=[false]], suppressEmail[attributes={}; value=[false]], campusCode[attributes={}; value=[HA]], externalIdentifiers[attributes={}; value=[]], employeeStatusCode[attributes={}; value=[A]], employeeTypeCode[attributes={}; value=[P]], primaryDepartmentCode[attributes={}; value=[HA-HA]], employeeId[attributes={}; value=[10329215]], baseSalaryAmount[attributes={}; value=[value[attributes={}; value=[0.00]]]], active[attributes={}; value=[true]]]], accountManagerUser[attributes={class=org.kuali.rice.kim.bo.impl.PersonImpl}; value=[principalId[attributes={}; value=[13168]], principalName[attributes={}; value=[noreeny]], entityId[attributes={}; value=[10333978]], entityTypeCode[attributes={}; value=[PERSON]], firstName[attributes={}; value=[Noreen]], middleName[attributes={}; value=[]], lastName[attributes={}; value=[Yamane]], name[attributes={}; value=[Yamane, Noreen]], addressLine1[attributes={}; value=[469 HOOKINA PL]], addressLine2[attributes={}; value=[]], addressLine3[attributes={}; value=[]], addressCityName[attributes={}; value=[HILO]], addressStateCode[attributes={}; value=[HI]], addressPostalCode[attributes={}; value=[96720]], addressCountryCode[attributes={}; value=[US]], emailAddress[attributes={}; value=[noreeny@hawaii.edu]], phoneNumber[attributes={}; value=[808-934-2504]], suppressName[attributes={}; value=[false]], suppressAddress[attributes={}; value=[false]], suppressPhone[attributes={}; value=[false]], suppressPersonal[attributes={}; value=[false]], suppressEmail[attributes={}; value=[false]], campusCode[attributes={}; value=[HA]], externalIdentifiers[attributes={}; value=[]], employeeStatusCode[attributes={}; value=[A]], employeeTypeCode[attributes={}; value=[P]], primaryDepartmentCode[attributes={}; value=[HA-HA]], employeeId[attributes={}; value=[10333978]], baseSalaryAmount[attributes={}; value=[value[attributes={}; value=[0.00]]]], active[attributes={}; value=[true]]]], accountGuideline[attributes={}; value=[chartOfAccountsCode[attributes={}; value=[HA]], accountNumber[attributes={}; value=[3028201]], accountExpenseGuidelineText[attributes={}; value=[Conform to university policy]], accountIncomeGuidelineText[attributes={}; value=[Conform to university policy]], accountPurposeText[attributes={}; value=[RTRF CC, HAWAII]], objectId[attributes={}; value=[C1835A5DCA8A3338E040A8C036E6319C]], newCollectionRecord[attributes={}; value=[false]], autoIncrementSet[attributes={}; value=[false]]]], accountDescription[attributes={}; value=[chartOfAccountsCode[attributes={}; value=[HA]], accountNumber[attributes={}; value=[3028201]], versionNumber[attributes={}; value=[1]], objectId[attributes={}; value=[C1835A5DCA8B3338E040A8C036E6319C]], newCollectionRecord[attributes={}; value=[false]], autoIncrementSet[attributes={}; value=[false]]]], versionNumber[attributes={}; value=[1]], objectId[attributes={}; value=[C1835A5DCA893338E040A8C036E6319C]], newCollectionRecord[attributes={}; value=[false]], extension[attributes={class=edu.hawaii.its.kfs.coa.businessobject.UhAccountExtension}; value=[chartOfAccountsCode[attributes={}; value=[HA]], accountNumber[attributes={}; value=[3028201]], uhRevenueFunctionCode[attributes={}; value=[151]], uhBudgetPlanID[attributes={}; value=[112000]], uhInterestIncomeAccountChart[attributes={}; value=[SW]], uhInterestIncomeAccountNbr[attributes={}; value=[4433020]], uhCostAcctgStandardsFlag[attributes={}; value=[false]], uhCostShareRequireFlag[attributes={}; value=[false]], uhExpandAuthorityFlag[attributes={}; value=[false]], uhIdcWaiverFlag[attributes={}; value=[false]], uhPreAwardFlag[attributes={}; value=[false]], uhProgramIncomeFlag[attributes={}; value=[false]], uhSubRecipientMonitorFlag[attributes={}; value=[false]], uhFundApprn[attributes={}; value=[R860CC]], uhCreateNewFyidAcctInd[attributes={}; value=[false]], uhSsfInd[attributes={}; value=[false]], uhNsfSurveyCode[attributes={}; value=[9999]], uhCoaCrosswalkIdent[attributes={}; value=[0000904]], uhFoCode[attributes={}; value=[090]], uhFmisGl[attributes={}; value=[028201]], uhUhFundApprnReference[attributes={}; value=[uhUhFundApprn[attributes={}; value=[R860CC]], uhUhFundApprnDesc[attributes={}; value=[RESEARCH AND TRAINING]], uhPre321FundApprn[attributes={}; value=[R860]], uhSsfFundApprn[attributes={}; value=[S362]], uhLegalAuthority[attributes={}; value=[304A-2253 HRS]], uhDepOfFund[attributes={}; value=[2]], uhNonImpFringeInd[attributes={}; value=[false]], active[attributes={}; value=[true]], ssfFndAppn[attributes={}; value=[uhSsfFundApprn[attributes={}; value=[S362]], uhSsfFundApprnDesc[attributes={}; value=[CC Systemwide Supp (Not in S/T)]], uhDagsFundApprn[attributes={}; value=[S380]], active[attributes={}; value=[true]], versionNumber[attributes={}; value=[1]], objectId[attributes={}; value=[B70FF529DFA9FA14E04010AC680456CB]], newCollectionRecord[attributes={}; value=[false]], autoIncrementSet[attributes={}; value=[false]]]], versionNumber[attributes={}; value=[1]], objectId[attributes={}; value=[B75F6555721B4546E04010AC68044B70]], newCollectionRecord[attributes={}; value=[false]], autoIncrementSet[attributes={}; value=[false]]]], versionNumber[attributes={}; value=[1]], objectId[attributes={}; value=[C1851877318B5632E040A8C036E64390]], newCollectionRecord[attributes={}; value=[false]], autoIncrementSet[attributes={}; value=[false]]]], autoIncrementSet[attributes={}; value=[false]]]]"
		ByteArrayInputStream bais=new ByteArrayInputStream(test2.getBytes("utf-8"))
		try {

		}catch(Exception e) {
		println " I am there"	
		}

		// Replace package names
		}
		return newXml;
	}

	/**
	 * Upgrades the old Bo notes tag that was part of the maintainable to the
	 * new notes tag.
	 *
	 * @param newXml
	 *            - the xml to upgrade
	 * @throws Exception
	 */
	private String upgradeBOs(String oldXml) throws Exception {
		// Get the old bo note xml
		String newXml = oldXml;
		String notesXml = StringUtils.substringBetween(oldXml, "<boNotes>", "</boNotes>");
		if (notesXml != null) {
			notesXml = notesXml.replace("org.kuali.rice.kns.bo.Note", "org.kuali.rice.krad.bo.Note");
			notesXml = "<org.apache.ojb.broker.core.proxy.ListProxyDefaultImpl>\n" + notesXml
			+ "\n</org.apache.ojb.broker.core.proxy.ListProxyDefaultImpl>";
			newXml = newXml.replaceFirst(">", ">\n<notes>\n" + notesXml + "\n</notes>");
		}
		return newXml;
	}

	/**
	 * Reads the rule xml and sets up the rule maps that will be used to transform the xml
	 */
	public List getFilesNames(String f) {
		try {
			System.out.println "hello there \n"
			Path xmlFilePath = Paths.get(f).toAbsolutePath()

			GPathResult sqls = new XmlSlurper().parse(xmlFilePath.toFile())
			List filenames = new ArrayList()
			List filenameswparms = new ArrayList()
			def fff
			sqls.depthFirst().findAll { node ->

				node.name() == 'file'
			}.each { fx ->
				filenames.add(fx.@name)

				fff  = fx.@name
				fx.depthFirst().findAll { noder ->
					noder.name() == 'parm'
				}.each { parmer ->
					fff = fff.toString() + parmer.text()
					print parmer.text()

				}
			fff =	fff.replace("\n", " ")
				filenameswparms.add(fff)
			}
			return filenames
			


		} catch (Exception e) {
			System.err.println("Error parsing rule xml file. Please check file. : ", e);
			System.exit(1);
		}
	}

	

}
