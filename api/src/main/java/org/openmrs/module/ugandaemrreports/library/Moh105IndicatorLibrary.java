package org.openmrs.module.ugandaemrreports.library;


import static org.openmrs.module.ugandaemrreports.UgandaEMRReportUtil.map;
import static org.openmrs.module.ugandaemrreports.reporting.utils.EmrReportingUtils.cohortIndicator;

import org.openmrs.Concept;
import org.openmrs.EncounterType;
import org.openmrs.api.context.Context;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.openmrs.module.ugandaemrreports.reporting.metadata.Dictionary;
import org.openmrs.module.ugandaemrreports.reporting.metadata.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class Moh105IndicatorLibrary {

    String ANC_UUID = Metadata.EncounterType.ANC_ENCOUNTER;
    String PNC_UUID = Metadata.EncounterType.PNC_ENCOUNTER;
    String EID_UUID = Metadata.EncounterType.EID_ENCOUNTER_PAGE;
    String MATERNITY_UUID = Metadata.EncounterType.MATERNITY_ENCOUNTER;
    @Autowired
    private Moh105CohortLibrary cohortLibrary;

    @Autowired
    private CommonCohortDefinitionLibrary cclibrary;

    /**
     * Number of female patients with ANC 1st visit
     */
    public CohortIndicator anc1stVisit(){

        return cohortIndicator("Patients who have ANC 1st Visit", map(cohortLibrary.femaleAndHasAncVisit(0.0, 1.0), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * Number of female patients with ANC 4th visit
     */
    public CohortIndicator anc4thVisit(){
        return cohortIndicator("Patients who have ANC 4th Visit", map(cohortLibrary.femaleAndHasAncVisit(3.0, 4.0), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * Number of female patients with ANC 4th visit and above
     */
    public CohortIndicator anc4thPlusVisit(){
        return cohortIndicator("Patients who have ANC 4th Visit and above", map(cohortLibrary.femaleAndHasAncVisit(4.0, 9.0), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * Total number of patients with ANC visits
     * @return CohortIndicator
     */
    public CohortIndicator totalAncVisits() {
        return cohortIndicator("Patients who have ANC Visits", map(cohortLibrary.femaleAndHasAncVisit(0.0, 9.0), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * Referral to ANC unit from community services
     * @return CohortIndicator
     */
    public CohortIndicator referalToAncUnitFromCommunityServices(){
        return cohortIndicator("Referral to ANC unit from community services", map(cohortLibrary.hasObsAndEncounter(ANC_UUID, Dictionary.getConcept("cd27f0ac-0fd3-4f40-99a3-57742106f5fd"), Dictionary.getConcept("03997d45-f6f7-4ee2-a6fe-b16985e3495d")), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * Referral to ANC unit total
     * @return CohortIndicator
     */
    public CohortIndicator referalToAncUnitTotal(){
        return cohortIndicator("Referral to ANC unit totals", map(cohortLibrary.hasObsAndEncounter(ANC_UUID, Dictionary.getConcept("cd27f0ac-0fd3-4f40-99a3-57742106f5fd"), Dictionary.getConcept("03997d45-f6f7-4ee2-a6fe-b16985e3495d"), Dictionary.getConcept("14714862-6c78-49da-b65b-f249cccddfb6")), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * Referral from ANC unit total
     * @return CohortIndicator
     */
    public CohortIndicator referalFromAncUnitTotal(){
        return cohortIndicator("Referral from ANC unit totals", map(cohortLibrary.hasObsAndEncounter(ANC_UUID, Dictionary.getConcept("cd27f0ac-0fd3-4f40-99a3-57742106f5fd"), Dictionary.getConcept("6442c9f6-25e8-4c8e-af8a-e9f6845ceaed"), Dictionary.getConcept("3af0aae4-4ea7-489d-a5be-c5339f7c5a77")), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }
    /**
     * Referral from ANC unit FSG
     * @return CohortIndicator
     */
    public CohortIndicator referalFromAncUniFsg(){
        return cohortIndicator("Referral from ANC unit FSG", map(cohortLibrary.hasObsAndEncounter(ANC_UUID, Dictionary.getConcept("cd27f0ac-0fd3-4f40-99a3-57742106f5fd"), Dictionary.getConcept("3af0aae4-4ea7-489d-a5be-c5339f7c5a77")), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * IPT(IPT1) Dose
     * @return CohortIndicator
     */
    public CohortIndicator iptDose(Concept answer){
        return cohortIndicator("Ipt dose on "+answer.getName().getName(), map(cohortLibrary.hasObsAndEncounter(ANC_UUID, Dictionary.getConcept("1da3cb98-59d8-4bfd-b0bb-c9c1bcd058c6"), answer), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * A9-Pregnant Women receiving Iron/Folic Acid on ANC 1 st Visit
     * @return CohortIndicator
     */
    public CohortIndicator pregnantAndReceivingIronOrFolicAcidAnc1stVisit() {
        return cohortIndicator("Pregnant Women receiving Iron/Folic Acid on ANC 1 st Visit", map(cohortLibrary.pregnantAndReceivingIronOrFolicAcidAnc1stVisit(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * Pregnant women having different obs
     * @return CohortIndicator
     */
    public CohortIndicator pregnantAndReceivingServices(Concept question, Concept answer) {
        return cohortIndicator("Receiving different servicess", map(cohortLibrary.hasObsAndEncounter(ANC_UUID, question, answer), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * Pregnant women tested for syphilis
     * @return CohortIndicator
     */
    public CohortIndicator pregnantAndTestedForSyphilis() {
        return cohortIndicator("Pregnant women tested for syphilis", map(cohortLibrary.hasObsAndEncounter(ANC_UUID, Dictionary.getConcept("275a6f72-b8a4-4038-977a-727552f69cb8")), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * Pregnant Women newly tested for HIV this pregnancy (TR & TRR)
     * @return CohortIndicator
     */
    public CohortIndicator pregnantWomenNewlyTestedForHivThisPregnancyTRAndTRR() {
        return cohortIndicator("Pregnant Women newly tested for HIV this pregnancy (TR & TRR)", map(cohortLibrary.hasObsAndEncounter(ANC_UUID, Dictionary.getConcept(Metadata.Concept.EMTCT_CODES), Dictionary.getConcept(Metadata.Concept.EMTCT_CODE_T), Dictionary.getConcept(Metadata.Concept.EMTCT_CODE_TR)), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * Pregnant Women tested HIV+ for 1st time this pregnancy (TRR) at any visit
     * @return CohortIndicator
     */
    public CohortIndicator pregnantWomenNewlyTestedForHivThisPregnancyTRR() {
        return cohortIndicator("Pregnant Women tested HIV+ for 1st time this pregnancy (TRR) at any visit", map(cohortLibrary.pregnantWomenNewlyTestedForHivThisPregnancyTRRAnyVisit(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    public CohortIndicator pregnantWomenNewlyTestedForHivThisPregnancyAndTestedHIVPositive() {
        return cohortIndicator("Pregnant Women tested HIV+ for 1st time this pregnancy (TRR) at any visit", map(cclibrary.hasANCObs(
		        Dictionary.getConcept(Metadata.Concept.EMTCT_CODES),  Dictionary.getConcept(Metadata.Concept.EMTCT_CODE_TRR)), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * HIV+ Pregnant women assessed by CD4 or WHO clinical stage the 1 time
     * @return CohortIndicator
     */
    public CohortIndicator hivPositiveAndAccessedWithCd4WhoStage(Concept question) {
        return cohortIndicator("Pregnant women assessed by "+question.getName().getName(), map(cohortLibrary.hivPositiveAndAccessedWithCd4WhoStage(question), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**Pregnant women assessed by
     * HIV+ Pregnant Women initiated on ART for EMTCT (ART)
     * @return CohortIndicator
     */
    public CohortIndicator hivPositiveInitiatedART() {
        return cohortIndicator("Pregnant Women tested HIV+ and initiated on ART", map(cohortLibrary.hasObsAndEncounter(ANC_UUID, Dictionary.getConcept("a615f932-26ee-449c-8e20-e50a15232763"),  Dictionary.getConcept("026e31b7-4a26-44d0-8398-9a41c40ff7d3")), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * Pregnant women who knew status before 1st ANC total (TRK+TRRK)
     * @return CohortIndicator
     */
    public CohortIndicator pregnantTrkTrrk() {
        return cohortIndicator("Pregnant women who knew status before 1st ANC total (TRK+TRRK)", map(cohortLibrary.hasObsAndEncounter(ANC_UUID, Dictionary.getConcept(Metadata.Concept.EMTCT_CODES), Dictionary.getConcept(Metadata.Concept.EMTCT_CODE_TRK), Dictionary.getConcept(Metadata.Concept.EMTCT_CODE_TRRK)), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * Pregnant women who knew status before 1st ANC total TRRK
     * @return CohortIndicator
     */
    public CohortIndicator pregnantTrrk() {
        return cohortIndicator("Pregnant women who knew status before 1st ANC total TRRK", map(cohortLibrary.hasObsAndEncounter(ANC_UUID, Dictionary.getConcept(Metadata.Concept.EMTCT_CODES), Dictionary.getConcept(Metadata.Concept.EMTCT_CODE_TRRK)), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * Pregnant Women already on ART before 1 ANC (ART-K)
     * @return CohortIndicator
     */
    public CohortIndicator alreadyOnARTK(){
        return cohortIndicator("Pregnant Women already on ART before 1 ANC (ART-K)", map(cohortLibrary.hasObsAndEncounter(ANC_UUID, Dictionary.getConcept("a615f932-26ee-449c-8e20-e50a15232763"), Dictionary.getConcept("2aa7d442-6cbb-4609-9dd3-bc2ad6f05016")), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * Pregnant Women re-tested later in pregnancy (TR+&TRR+)
     * @return CohortIndicator
     */
    public CohortIndicator retestedTrTrrPlus(){
        return cohortIndicator("Pregnant Women re-tested later in pregnancy (TR+&TRR+))", map(cohortLibrary.hasObsAndEncounter(ANC_UUID, Dictionary.getConcept(Metadata.Concept.EMTCT_CODES), Dictionary.getConcept(Metadata.Concept.EMTCT_CODE_TRK), Dictionary.getConcept(Metadata.Concept.EMTCT_CODE_TRRK)), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * Pregnant Women testing HIV+ on a retest (TRR+)
     * @return CohortIndicator
     */
    public CohortIndicator retestedTrrPlus() {
        return cohortIndicator("Pregnant Women testing HIV+ on a retest (TRR+)", map(cohortLibrary.hasObsAndEncounter(ANC_UUID, Dictionary.getConcept(Metadata.Concept.EMTCT_CODES), Dictionary.getConcept(Metadata.Concept.EMTCT_CODE_TRP)), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * HIV+ Pregnant Women initiated on Cotrimoxazole
     * @return CohortIndicator
     */
    public CohortIndicator initiatedOnCtx() {
        return cohortIndicator("Pregnant Women initiated on Cotrimoxazole", map(cohortLibrary.hasObsAndEncounter(ANC_UUID, Dictionary.getConcept("1da3cb98-59d8-4bfd-b0bb-c9c1bcd058c6"), Dictionary.getConcept("fca28768-50dc-4d6b-a3d2-2aae3b376b27")), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * Male partiners received HIV test results in eMTCT total
     * @return CohortIndicator
     */
    public CohortIndicator malePatinersRecievedHivResultTotal() {
        return cohortIndicator("Male partners received HIV test results in eMTCT - Totals", map(cohortLibrary.hasObsAndEncounter(ANC_UUID, Dictionary.getConcept("62a37075-fc2a-4729-8950-b9fae9b22cfb"), Dictionary.getConcept(Metadata.Concept.EMTCT_CODE_TRR), Dictionary.getConcept(Metadata.Concept.EMTCT_CODE_TR)), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     *Male partiners received HIV test results in eMTCT HIV+
     * @return CohortIndicator
     */
    public CohortIndicator malePatinersRecievedHivResultHivPositive() {
        return cohortIndicator("Male partners received HIV test results in eMTCT - HIV+", map(cohortLibrary.hasObsAndEncounter(ANC_UUID, Dictionary.getConcept("62a37075-fc2a-4729-8950-b9fae9b22cfb"), Dictionary.getConcept(Metadata.Concept.EMTCT_CODE_TRR)), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * Post natal attendances totals
     * @return CohortIndicator
     */
    public CohortIndicator pncAttendances() {
        return cohortIndicator("Total attendances", map(cclibrary.hasEncounter(Context.getEncounterService().getEncounterTypeByUuid(PNC_UUID)), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * Post natal attendances totals
     * @return CohortIndicator
     */
    public CohortIndicator pncAttendances(String ans) {
        return cohortIndicator("Total attendances Timing", map(cohortLibrary.hasObsAndEncounter(PNC_UUID, Dictionary.getConcept("1732AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"), Dictionary.getConcept(ans)), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * The observations ahs some answers collected
     */
    public CohortIndicator hasObs(String q, String ans) {
        return cohortIndicator("Has some obs", map(cohortLibrary.hasObsAndEncounter(PNC_UUID, Dictionary.getConcept(q), Dictionary.getConcept(ans)), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * Total HIV+ mothers attending postnatal
     * @return CohortIndicator
     */
    public CohortIndicator totaHivPositiveMothers() {
        return cohortIndicator("Total HIV+ mothers attending postnatal", map(cohortLibrary.hivPositiveMothersInAnc(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * Hiv+ women initiating ART in PNC
     * @return CohortIndicator
     */
    public CohortIndicator hivPositiveWomenInitiatingART() {
        Concept art = Dictionary.getConcept("a615f932-26ee-449c-8e20-e50a15232763");
        Concept ans = Dictionary.getConcept("026e31b7-4a26-44d0-8398-9a41c40ff7d3");

        return cohortIndicator("Hiv positive women initiating ART in PNC", map(cohortLibrary.hasObsAndEncounter(PNC_UUID, art, ans), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * Mother-baby pairs enrolled at Mother-Baby care point
     * @return CohortIndicator
     */
    public CohortIndicator enrolledAtMotherBabyCarePoint() {
        return cohortIndicator("Mother-baby pairs enrolled at Mother-Baby care point", map(cohortLibrary.motherBabyEnrolled(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * Breastfeeding mothers tested for HIV 1st test during postnatal
     * @return CohortIndicator
     */
    public CohortIndicator breastFeedingMothersTestedForHIVFirstTestDuringPostnatal() {
        Concept t = Dictionary.getConcept(Metadata.Concept.EMTCT_CODE_T);
        Concept tr = Dictionary.getConcept(Metadata.Concept.EMTCT_CODE_TR);
        Concept trr = Dictionary.getConcept(Metadata.Concept.EMTCT_CODE_TRR);
        Concept trrTick = Dictionary.getConcept(Metadata.Concept.EMTCT_CODE_TRRK);
        Concept trTick = Dictionary.getConcept(Metadata.Concept.EMTCT_CODE_TRK);
        Concept question = Dictionary.getConcept(Metadata.Concept.EMTCT_CODES);
        return cohortIndicator("Breastfeeding mothers tested for HIV 1st test during postnatal", map(cohortLibrary.hasObsAndEncounter(PNC_UUID, question, t, tr, trTick, trrTick, trr), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * Breastfeeding mothers tested for HIV 1st test during postnatal
     * @return CohortIndicator
     */
    public CohortIndicator breastFeedingMothersTestedForHIVReTestDuringPostnatal() {
        Concept question = Dictionary.getConcept(Metadata.Concept.EMTCT_CODES);
        Concept trPlus = Dictionary.getConcept(Metadata.Concept.EMTCT_CODE_TRP);
        Concept trrPlus = Dictionary.getConcept(Metadata.Concept.EMTCT_CODE_TRRP);

        return cohortIndicator("Breastfeeding mothers tested for HIV re test during postnatal", map(cohortLibrary.hasObsAndEncounter(PNC_UUID, question, trPlus, trrPlus), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * Breastfeeding mothers tested for HIV+ 1st test during postnatal
     * @return CohortIndicator
     */
    public CohortIndicator breastFeedingMothersTestedForHIVPositiveTestDuringPostnatal() {
        Concept question = Dictionary.getConcept(Metadata.Concept.EMTCT_CODES);
        Concept trPlus = Dictionary.getConcept(Metadata.Concept.EMTCT_CODE_TRR);
        Concept trrPlus = Dictionary.getConcept(Metadata.Concept.EMTCT_CODE_TRRK);

        return cohortIndicator("Breastfeeding mothers tested for HIV+  test during postnatal", map(cohortLibrary.hasObsAndEncounter(PNC_UUID, question, trPlus, trrPlus), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * Breastfeeding mothers tested for HIV+ retest test during postnatal
     * @return CohortIndicator
     */
    public CohortIndicator breastFeedingMothersTestedForHIVPositiveReTestDuringPostnatal() {
        Concept question = Dictionary.getConcept(Metadata.Concept.EMTCT_CODES);
        Concept trPlus = Dictionary.getConcept(Metadata.Concept.EMTCT_CODE_TRP);


        return cohortIndicator("Breastfeeding mothers tested for HIV+  test during postnatal", map(cohortLibrary.hasObsAndEncounter(PNC_UUID, question, trPlus), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }
    
    /**
     * Missed appointment in the period
     * @return
     */
    public CohortIndicator missedANCAppointment() {
        return cohortIndicator("Patients who have ANC 1st Visit", map(cohortLibrary.missedANCAppointment(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * Visit for women - No in 1st trimester
     * @return CohortIndicator
     */
    public CohortIndicator visitsForWomenInFirstTrimester(){
        Concept anc1 = Dictionary.getConcept("3a862ab6-7601-4412-b626-d373c1d4a51e");
        return cohortIndicator("Visit for women - No in 1st trimester", map(cohortLibrary.hasObsAndEncounter(ANC_UUID, anc1, Dictionary.getConcept("1065AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     *Maternity Admissions
     * @return CohortIndicator
     */
    public CohortIndicator maternityAdmissions() {
        return cohortIndicator("Maternity admissions", map(cohortLibrary.maternityAdmissions(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }
    
    /**
     *Referrals to Maternity Unit
     * @return CohortIndicator
     */
    public CohortIndicator referralsToMaternityUnit() {
        return cohortIndicator("Referrals to maternity unit", map(cohortLibrary.referralsToMaternityUnit(), "onOrAfter=${startDate},onOrBefore=${endDate}"));

    }

    /**
     *Referrals out of Maternity Unit
     * @return CohortIndicator
     */
    public CohortIndicator maternityReferralsOut() {
        return cohortIndicator("Maternity referrals Out", map(cohortLibrary.maternityReferralsOut(), "onOrAfter=${startDate},onOrBefore=${endDate}"));

    }
    
    /**
     *Deliveries in unit - Total
     * @return CohortIndicator
     */
    public CohortIndicator deliveriesInUnit() {
        return cohortIndicator("Deliveries in unit", map(cohortLibrary.deliveriesInUnit(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }
    
    /**
     *Fresh still birth deliveries
     * @return CohortIndicator
     */
    public CohortIndicator freshStillBirthDeliveries() {
        return cohortIndicator("Fresh still birth", map(cohortLibrary.hasObsAndEncounter(MATERNITY_UUID, Dictionary.getConcept("a5638850-0cb4-4ce8-8e87-96fc073de25d"), Dictionary.getConcept("7a15616a-c12a-44fc-9a11-553639128b69")), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     *Macerated still birth deliveries
     * @return CohortIndicator
     */
    public CohortIndicator maceratedStillBirthDeliveries() {
        return cohortIndicator("Macerated still birth", map(cohortLibrary.hasObsAndEncounter(MATERNITY_UUID, Dictionary.getConcept("a5638850-0cb4-4ce8-8e87-96fc073de25d"), Dictionary.getConcept("fda5ad21-6ba4-4526-a0f3-ea1269d43422")), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     *Live births
     * @return CohortIndicator
     */
    public CohortIndicator liveBirthDeliveries() {
        return cohortIndicator("Live births", map(cohortLibrary.hasObsAndEncounter(MATERNITY_UUID, Dictionary.getConcept("a5638850-0cb4-4ce8-8e87-96fc073de25d"), Dictionary.getConceptList("eb7041a0-02e6-4e9a-9b96-ff65dd09a416,23ac7575-f0ea-49a5-855e-b3348ad1da01")), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }
    
    /**
     *Pre-Term births
     * @return CohortIndicator
     */
    public CohortIndicator pretermBirthDeliveries() {
        return cohortIndicator("Pre-Term births", map(cohortLibrary.hasObsAndEncounter(MATERNITY_UUID, Dictionary.getConcept("161033AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"), Dictionary.getConcept("129218AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }
    
    /**
     *M5: Women tested for HIV in labour - 1st time this Pregnancy
     * @return CohortIndicator
     */
    public CohortIndicator womenTestedForHivInLabourFirstTimePregnancy() {
        return cohortIndicator("Women tested for HIV in labour - 1st time this Pregnancy", map(cohortLibrary.hasObsAndEncounter(MATERNITY_UUID, Dictionary.getConcept(Metadata.Concept.EMTCT_CODES), Dictionary.getConceptList("81bd3e58-9389-41e7-be1a-c6723f899e56,1f177240-85f6-4f10-964a-cfc7722408b3")), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     *M5: Women tested for HIV in labour - Retest this Pregnancy
     * @return CohortIndicator
     */
    public CohortIndicator womenTestedForHivInLabourRetestThisPregnancy() {
        return cohortIndicator("Women tested for HIV in labour - Retest this Pregnancy", map(cohortLibrary.hasObsAndEncounter(MATERNITY_UUID, Dictionary.getConcept(Metadata.Concept.EMTCT_CODES), Dictionary.getConceptList("05f16fc5-1d82-4ce8-9b44-a3125fbbf2d7,86e394fd-8d85-4cb3-86d7-d4b9bfc3e43a,25c448ff-5fe4-4a3a-8c0a-b5aaea9d5465,60155e4d-1d49-4e97-9689-758315967e0f")), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     *M6: Women testing HIV+ in labour - 1st time this Pregnancy
     * @return CohortIndicator
     */
    public CohortIndicator womenTestingHivPositiveInLabourFirstTimePregnancy() {
        return cohortIndicator("M6: Women testing HIV+ in labour - 1st time this Pregnancy", map(cohortLibrary.hasObsAndEncounter(MATERNITY_UUID, Dictionary.getConcept(Metadata.Concept.EMTCT_CODES), Dictionary.getConcept(Metadata.Concept.EMTCT_CODE_TRRK)), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     *M6: Women testing HIV+ in labour - Retest this Pregnancy
     * @return CohortIndicator
     */
    public CohortIndicator womenTestingHivPositiveInLabourRetestThisPregnancy() {
        return cohortIndicator("M6: Women testing HIV+ in labour - Retest this Pregnancy", map(cohortLibrary.hasObsAndEncounter(MATERNITY_UUID, Dictionary.getConcept(Metadata.Concept.EMTCT_CODES), Dictionary.getConceptList("25c448ff-5fe4-4a3a-8c0a-b5aaea9d5465,60155e4d-1d49-4e97-9689-758315967e0f")), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     *M7: HIV+ women initiating ART in maternity
     * @return CohortIndicator
     */
    public CohortIndicator hivPositiveWomenInitiatingArtInMaternity() {
        return cohortIndicator("HIV+ women initiating ART in maternity", map(cohortLibrary.hivPositiveWomenInitiatingArvInMaternity(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     *M8: Deliveries to HIV+ women in unit - Total
     * @return CohortIndicator
     */
    public CohortIndicator deliveriesTohivPositiveWomen() {
        return cohortIndicator("Deliveries to HIV+ women", map(cohortLibrary.deliveriesToHIVPositiveWomen(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     *M8: Deliveries to HIV+ women in unit - Live births
     * @return CohortIndicator
     */
    public CohortIndicator liveBirthDeliveriesTohivPositiveWomen() {
        return cohortIndicator("Live birth deliveries to HIV+ women", map(cohortLibrary.liveBirthDeliveriesToHIVPositiveWomen(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }
    
    /**
     *M9: HIV Exposed babies given ARVs
     * @return CohortIndicator
     */
    public CohortIndicator hivExposedBabiesGivenArvs() {
        return cohortIndicator("HIV exposed babies given ARVs", map(cohortLibrary.hasObsAndEncounter(MATERNITY_UUID, Dictionary.getConcept(Metadata.Concept.ARV_FOR_BABY), Dictionary.getConcept(Metadata.Concept.NVP_SYRUP)), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     *M10: No. of mothers who initiated breastfeeding within the 1st hour after delivery - Total
     * @return CohortIndicator
     */
    public CohortIndicator initiatedBreastfeedingWithinFirstHourAfterDelivery() {
        return cohortIndicator("Initiated breastfeeding within the 1st hour after delivery", map(cohortLibrary.initiatedBreastfeedingWithinFirstHourAfterDelivery(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }
    
    /**
     *M10: No. of mothers who initiated breastfeeding within the 1st hour after delivery - HIV+
     * @return CohortIndicator
     */
    public CohortIndicator initiatedBreastfeedingWithinFirstHourAfterDeliveryAndHivPositive() {
        return cohortIndicator("Initiated breastfeeding within the 1st hour after delivery and HIV+", map(cohortLibrary.initiatedBreastfeedingWithinFirstHourAfterDeliveryAndHIVPositive(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     *M11: Babies born with low birth weight (<2.5kg)
     * @return CohortIndicator
     */
    public CohortIndicator babiesBornWithLowBirthWeight() {
        return cohortIndicator("Babies born with low birthweight", map(cohortLibrary.babiesBornWithLowBirthWeight(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     *M12: Live babies
     * @return CohortIndicator
     */
    public CohortIndicator liveBabies() {
        return cohortIndicator("Live babies", map(cohortLibrary.hasObsAndEncounter(MATERNITY_UUID, Dictionary.getConcept("a5638850-0cb4-4ce8-8e87-96fc073de25d"),Dictionary.getConceptList("eb7041a0-02e6-4e9a-9b96-ff65dd09a416,23ac7575-f0ea-49a5-855e-b3348ad1da01")), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }
    
    /**
     *M13: Babies born with defect
     * @return CohortIndicator
     */
    public CohortIndicator babiesBornWithDefect() {
        return cohortIndicator("Babies born with defect", map(cohortLibrary.hasObsAndEncounter(MATERNITY_UUID, Dictionary.getConcept("a5638850-0cb4-4ce8-8e87-96fc073de25d"),Dictionary.getConcept("23ac7575-f0ea-49a5-855e-b3348ad1da01")), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }
    
    /**
     *M15: Newborn deaths (0-7 days)
     * @return CohortIndicator
     */
    public CohortIndicator newBornDeaths() {
        return cohortIndicator("New born deaths", map(cohortLibrary.hasObsAndEncounter(MATERNITY_UUID, Dictionary.getConcept("a5638850-0cb4-4ce8-8e87-96fc073de25d"),Dictionary.getConcept("ab3a7679-f5ee-48d6-b690-f55a1dfe95ea")), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     *M16: Maternal deaths
     * @return CohortIndicator
     */
    public CohortIndicator maternalDeaths() {
        return cohortIndicator("Maternal deaths", map(cohortLibrary.maternalDeaths(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }    
    
    /**
     *M18: Birth asphyxia
     * @return CohortIndicator
     */
    public CohortIndicator birthAsphyxia() {
        return cohortIndicator("Birth asphyxia", map(cohortLibrary.hasObsAndEncounter(MATERNITY_UUID, Dictionary.getConcept(Metadata.Concept.ASPHYXIA), Dictionary.getConcept(Metadata.Concept.YES_CIEL)), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     *M19: No. of babies who received PNC at 6 hours
     * @return CohortIndicator
     */
    public CohortIndicator babiesReceivedPncAt6Hours() {
        return cohortIndicator("Babies received PNC at 6 hours", map(cohortLibrary.hasObsAndEncounter(MATERNITY_UUID, Dictionary.getConcept("93ca1215-5346-4fde-8905-84e930d9f1c1")), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }
    
	/**
	 * HIV Exposed Infants - EID
	 */
	
	/**
	 * E1A Exposed infants tested for HIV below 18 months of age - 1st PCR
	 * 
	 * @return CohortIndicator
	 */
	public CohortIndicator exposedInfantsTestedForHivBelow18MonthsOfAge1StPcr() {
		return cohortIndicator("Exposed infants tested for HIV below 18 months of age - 1st PCR",
		    map(cohortLibrary.hasObsAndEncounter(EID_UUID, Dictionary.getConcept(Metadata.Concept.DATE_OF_FIRST_PCR_TEST)),
		        "onOrAfter=${startDate},onOrBefore=${endDate}"));
	}
	
	/**
	 * E1B Exposed infants tested for HIV below 18 months of age - 2nd PCR
	 * 
	 * @return CohortIndicator
	 */
	public CohortIndicator exposedInfantsTestedForHivBelow18MonthsOfAge2NdPcr() {
		return cohortIndicator("Exposed infants tested for HIV below 18 months of age - 2nd PCR",
		    map(cohortLibrary.hasObsAndEncounter(EID_UUID, Dictionary.getConcept(Metadata.Concept.DATE_OF_SECOND_PCR_TEST)),
		        "onOrAfter=${startDate},onOrBefore=${endDate}"));
	}
	
	/**
	 * E1C Exposed infants tested for HIV below 18 months of age - 18month rapid test
	 * 
	 * @return CohortIndicator
	 */
	public CohortIndicator exposedInfantsTestedForHivBelow18MonthsOfAge18MonthRapidTest() {
		return cohortIndicator("Exposed infants tested for HIV below 18 months of age - 18month rapid test",
		    map(cohortLibrary.hasObsAndEncounter(EID_UUID,
		        Dictionary.getConcept(Metadata.Concept.DATE_OF_18_MONTH_RAPID_PCR_TEST)),
		        "onOrAfter=${startDate},onOrBefore=${endDate}"));
	}
	
	/**
	 * E2A 1st DNA PCR result returned
	 * 
	 * @return CohortIndicator
	 */
	public CohortIndicator firstDnaPcrResultReturned() {
		return cohortIndicator("1st DNA PCR result returned",
		    map(cohortLibrary.firstDnaPCRResultsReturnedFromTheLab(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
	}
	
	/**
	 * E2B 1st DNA PCR result returned - HIV+
	 * 
	 * @return CohortIndicator
	 */
	public CohortIndicator firstDnaPcrResultReturnedHivPositive() {
		return cohortIndicator("1st DNA PCR result returned - HIV+",
		    map(cohortLibrary.firstDnaPCRResultsReturnedFromTheLabHivPositive(),
		        "onOrAfter=${startDate},onOrBefore=${endDate}"));
	}
	
	/**
	 * E3A 2nd DNA PCR result returned
	 * 
	 * @return CohortIndicator
	 */
	public CohortIndicator secondDnaPcrResultReturned() {
		return cohortIndicator("2nd DNA PCR result returned",
		    map(cohortLibrary.secondDnaPCRResultsReturnedFromTheLab(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
	}
	
	/**
	 * E3B 2nd DNA PCR result returned - HIV+
	 * 
	 * @return CohortIndicator
	 */
	public CohortIndicator secondDnaPcrResultReturnedHivPositive() {
		return cohortIndicator("2nd DNA PCR result returned - HIV+",
		    map(cohortLibrary.secondDnaPCRResultsReturnedFromTheLabHivPositive(),
		        "onOrAfter=${startDate},onOrBefore=${endDate}"));
	}
	
	/**
	 * E4A Number of DNA PCR results returned from the lab
	 * 
	 * @return CohortIndicator
	 */
	public CohortIndicator numberOfDnaPcrResultsReturnedFromTheLab() {
		return cohortIndicator("Number of DNA PCR results returned from the lab",
		    map(cohortLibrary.dnaPcrResultsReturnedFromTheLab(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
	}
	
	/**
	 * E4B Number of DNA PCR results returned from the lab - given to care giver
	 * 
	 * @return CohortIndicator
	 */
	public CohortIndicator dnaPcrResultsReturnedFromTheLabGivenToCareGiver() {
		return cohortIndicator("Number of DNA PCR results returned from the lab - given to care giver",
		    map(cohortLibrary.dnaPcrResultsReturnedFromTheLabGivenToCareGiver(),
		        "onOrAfter=${startDate},onOrBefore=${endDate}"));
	}
	
	/**
	 * E5A Number of HIV Exposed infants tested by serology/rapidHIV test at 18 months
	 * 
	 * @return CohortIndicator
	 */
	public CohortIndicator hivExposedInfantsTestedBySerologyRapidhivTestAt18Months() {
		return cohortIndicator("Number of HIV Exposed infants tested by serology/rapidHIV test at 18 months",
		    map(cohortLibrary.hasObsAndEncounter(EID_UUID,
		        Dictionary.getConcept(Metadata.Concept.RAPID_HIV_TEST_AT_18_MONTHS_DATE)),
		        "onOrAfter=${startDate},onOrBefore=${endDate}"));
	}
	
	/**
	 * E5B Number of HIV Exposed infants tested by serology/rapidHIV test at 18 months - HIV+
	 * 
	 * @return CohortIndicator
	 */
	public CohortIndicator hivExposedInfantsTestedBySerologyRapidhivTestAt18MonthsHivPositive() {
		return cohortIndicator("Number of HIV Exposed infants tested by serology/rapidHIV test at 18 months - HIV+",
		    map(cohortLibrary.hasObsAndEncounter(EID_UUID,
		        Dictionary.getConcept(Metadata.Concept.RAPID_HIV_TEST_AT_18_MONTHS_TEST_RESULT),
		        Dictionary.getConcept(Metadata.Concept.POSITIVE)), "onOrAfter=${startDate},onOrBefore=${endDate}"));
	}
	
	/**
	 * E6A Number of HIV+ infants from EID enrolled in care
	 * 
	 * @return CohortIndicator
	 */
	public CohortIndicator hivPositiveInfantsFromEidEnrolledInCare() {
		return cohortIndicator("Number of HIV+ infants from EID enrolled in care",
		    map(cohortLibrary.hivPositiveInfantsFromEidEnrolledInCare(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
	}
	
	/**
	 * E7 HIV exposed infants started on CPT
	 * 
	 * @return CohortIndicator
	 */
	public CohortIndicator hivExposedInfantsStartedOnCpt() {
		return cohortIndicator("HIV exposed infants started on CPT",
		    map(cohortLibrary.hasObsAndEncounter(EID_UUID, Dictionary.getConcept(Metadata.Concept.STARTED_ON_CPT)),
		        "onOrAfter=${startDate},onOrBefore=${endDate}"));
	}
	
	/**
	 * Tetanus Immunization dose
	 * 
	 * @return CohortIndicator
	 */
	public CohortIndicator tetanusImmunizationsDone(int doseNumber, boolean pregnant) {
		return cohortIndicator("Tetanus Immunization dose. Dose# " + doseNumber,
		    map(cohortLibrary.tetanusImmunizationsDone(doseNumber, pregnant), "onOrAfter=${startDate},onOrBefore=${endDate}"));
	}
	
	//indicators for the SMC section follow here

    /**
     * counselled and tested for HIV with results
     * @return CohortIndicator
     */
    public CohortIndicator counseledAndTestedWithResuls(Concept ans) {
        return cohortIndicator("Counseled and tested with results", map(cohortLibrary.counseledTestedForHivResults(ans), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * counselled and tested for HIV regardless of the results
     * @return CohortIndicator
     */
    public CohortIndicator counseledAndTested() {
        return cohortIndicator("Counseled and tested", map(cohortLibrary.counseledTestedForHiv(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * expected number of SMC performed(monthly targets)
     * @return cohort indicator
     */
    public CohortIndicator expectedNumberOfSmcPerfomed() {
        EncounterType smc = MetadataUtils.existing(EncounterType.class, "244da86d-f80e-48fe-aba9-067f241905ee");
        return cohortIndicator("expected number of SMC performed", map(cclibrary.hasEncounter(smc), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * Clients circumcised who experinced one or more adverse events
     * @return CohortIndicator
     */
    public CohortIndicator circumcisedAndExperiencedAdverseEvents(Concept ... ans){
        Concept adQuestion = Dictionary.getConcept("e34976b9-1aff-489d-b959-4da1f7272499");
        return cohortIndicator("circumcised and AE", map(cclibrary.hasObs(adQuestion, ans), "onOrAfter=${startDate},onOrBefore=${endDate}"));

    }

    /**
     * Clients circumcised by used Technique
     * @return CohortIndicator
     */
    public CohortIndicator clientsCircumcisedWithTechnique(Concept ... ans) {
        Concept techQuestion = Dictionary.getConcept("bd66b11f-04d9-46ed-a367-2c27c15d5c71");
        return cohortIndicator("circumcised and AE", map(cclibrary.hasObs(techQuestion, ans), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }
    
    /**
     * Clients circumcised by used surgical Technique
     * @return CohortIndicator
     */
    public CohortIndicator clientsCircumcisedWithSurgicalTechnique() {
        return cohortIndicator("circumcised and AE", map(cohortLibrary.surgicalProcedureMethod(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * follow up visits in SMC
     * @return CohortIndicator
     */
    public CohortIndicator smcFollowUps(int visit){
        return cohortIndicator("follow up visits", map(cohortLibrary.clientsCircumcisedAndReturnedWithin6WeeksAndHaveSmcEncounter(visit), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    //Begin HIV/AIDS counseling and testing (HCT)
    
    /**
     * H1-Number of Individuals counseled
     * @return CohortIndicator
     */
    public CohortIndicator individualsCounselled() {
        return cohortIndicator("Number of Individuals counseled", map(cohortLibrary.counseledAsIndividuals(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * H2-Number of Individuals tested
     * @return CohortIndicator
     */
    public CohortIndicator individualsTested() {
        return cohortIndicator("Number of Individuals tested", map(cohortLibrary.individualsTested(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }
    
    /**
     * H3-Number of Individuals who received HIV test results
     * @return CohortIndicator
     */
    public CohortIndicator individualsWhoReceivedHIVTestResults() {
        return cohortIndicator("Number of Individuals who received HIV test results", map(cohortLibrary.individualsWhoReceivedHIVTestResults(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * H4- Number of individuals who received HIV results in the last 12months
     * @return CohortIndicator
     */
    public CohortIndicator individualsWhoReceivedHIVTestResultsInLast12Months() {
        return cohortIndicator("Number of Individuals who received HIV test results in last 12 Months", map(cohortLibrary.individualsWhoReceivedHIVTestResultsInLast12Months(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }
    
    /**
     * H5 – Number of individuals tested for the first time
     * @return CohortIndicator
     */
    public CohortIndicator individualsTestedForTheFirstTime() {
        return cohortIndicator("Number of individuals tested for the first time", map(cohortLibrary.individualsTestedFirstTime(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }
    
    /**
     * H6-Number of Individuals who tested HIV positive
     * @return CohortIndicator
     */
    public CohortIndicator individualsWhoTestedHivPositive() {
        return cohortIndicator("Number of Individuals who tested HIV positive", map(cohortLibrary.individualsWhoTestedHivPositive(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * H7-HIV positive individuals with presumptive TB
     * @return CohortIndicator
     */
    public CohortIndicator individualsWhoTestedHivPositiveAndWithPresumptiveTb() {
        return cohortIndicator("HIV positive individuals with presumptive TB", map(cohortLibrary.individualsWhoTestedHivPositiveAndWithPresumptiveTB(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }
    
    /**
     * H8-Number of Individuals tested more than twice in the last 12 months
     * @return CohortIndicator
     */
    public CohortIndicator individualsTestedMoreThanTwiceInLast12Months() {
        return cohortIndicator("Number of Individuals tested more than twice in the last 12 months", map(cohortLibrary.individualsTestedMoreThanTwiceInLast12Months(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * H9-Number of individuals who were Counseled and Tested together as a Couple
     * @return CohortIndicator
     */
    public CohortIndicator individualsCounseledAndTestedAsCouple() {
        return cohortIndicator("Number of individuals who were Counseled and Tested together as a Couple", map(cohortLibrary.individualsCounseledAndTestedAsACouple(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * H10-Number of individuals who were Tested and Received results together as a Couple
     * @return CohortIndicator
     */
    public CohortIndicator individualsTestedAndReceivedResultsAsACouple() {
        return cohortIndicator("Number of individuals who were Tested and Received results together as a Couple", map(cohortLibrary.individualsTestedAndReceivedResultsAsACouple(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * H11-Number of couples with Concordant positive results
     * @return CohortIndicator
     */
    public CohortIndicator couplesWithConcordantPositiveResults() {
        return cohortIndicator("Number of couples with Concordant positive results", map(cohortLibrary.couplesWithConcordantPostiveResults(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }    

    /**
     * H12- Number of couples with Discordant results
     * @return CohortIndicator
     */
    public CohortIndicator couplesWithDiscordantResults() {
        return cohortIndicator("Number of couples with Disconcordant results", map(cohortLibrary.couplesWithDiscordantResults(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }    
    
    /**
     * H13-Individuals counseled and tested for PEP
     * @return CohortIndicator
     */
    public CohortIndicator individualsCounselledAndTestedForPep() {
        return cohortIndicator("Individuals counseled and tested for PEP", map(cohortLibrary.individualsCounseledAndTestedForPep(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * H14-Number of individuals tested as MARPS
     * @return CohortIndicator
     */
    public CohortIndicator individualsCounselledAndTestedMarps() {
        return cohortIndicator("Individuals counseled and tested as MARPS", map(cohortLibrary.individualsCounseledAndTestedAsMarps(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }

    /**
     * H15-Number of positive individuals who tested at an early stage (CD4>500µ)
     * @return CohortIndicator
     */
    public CohortIndicator hivPositiveIndividualsTestedAtAnEarlyStage() {
        return cohortIndicator("Number of positive individuals who tested at an early stage (CD4>500µ)", map(cohortLibrary.hivPositiveIndividualsTestedAtAnEarlyStage(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }
    
    /**
     * H16-Number of clients who have been linked to care
     * @return CohortIndicator
     */
    public CohortIndicator clientsLinkedToCare() {
        return cohortIndicator("Number of clients who have been linked to care", map(cohortLibrary.clientsLinkedToCare(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
    }
    //End HIV/AIDS counseling and testing (HCT)        
	
}