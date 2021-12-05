package com.clement.advent2020.day.nine;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.AssertionErrors;

class DayNineTest {
	private static final Logger log = LoggerFactory.getLogger(DayNineTest.class);

	List<Long> inputs = Arrays.asList(
			17L
			,31L
			,1L
			,10L
			,41L
			,37L
			,24L
			,22L
			,35L
			,11L
			,49L
			,4L
			,48L
			,5L
			,46L
			,15L
			,7L
			,45L
			,6L
			,30L
			,12L
			,19L
			,33L
			,9L
			,50L
			,8L
			,13L
			,14L
			,16L
			,10L
			,17L
			,18L
			,21L
			,22L
			,34L
			,84L
			,11L
			,58L
			,23L
			,60L
			,20L
			,28L
			,15L
			,42L
			,26L
			,24L
			,41L
			,19L
			,25L
			,27L
			,37L
			,29L
			,31L
			,30L
			,32L
			,33L
			,35L
			,47L
			,39L
			,64L
			,34L
			,36L
			,40L
			,38L
			,43L
			,44L
			,45L
			,81L
			,54L
			,46L
			,48L
			,49L
			,50L
			,52L
			,56L
			,59L
			,60L
			,91L
			,114L
			,82L
			,69L
			,97L
			,70L
			,76L
			,72L
			,74L
			,78L
			,108L
			,90L
			,87L
			,98L
			,93L
			,99L
			,141L
			,106L
			,125L
			,128L
			,102L
			,111L
			,115L
			,119L
			,129L
			,139L
			,142L
			,214L
			,143L
			,217L
			,150L
			,146L
			,152L
			,161L
			,165L
			,218L
			,177L
			,202L
			,339L
			,363L
			,201L
			,208L
			,352L
			,213L
			,258L
			,304L
			,226L
			,234L
			,262L
			,540L
			,376L
			,289L
			,589L
			,370L
			,302L
			,441L
			,420L
			,313L
			,326L
			,379L
			,378L
			,411L
			,421L
			,409L
			,739L
			,681L
			,434L
			,439L
			,447L
			,523L
			,460L
			,488L
			,643L
			,564L
			,615L
			,591L
			,698L
			,733L
			,881L
			,1341L
			,639L
			,826L
			,790L
			,704L
			,799L
			,787L
			,1062L
			,830L
			,843L
			,873L
			,1099L
			,1273L
			,1328L
			,1262L
			,948L
			,1024L
			,1314L
			,1206L
			,1179L
			,1230L
			,1289L
			,1547L
			,1426L
			,1343L
			,1512L
			,1438L
			,1491L
			,1586L
			,1503L
			,1617L
			,1854L
			,1673L
			,1897L
			,2546L
			,1972L
			,3315L
			,2522L
			,2127L
			,2154L
			,3827L
			,2203L
			,2385L
			,4588L
			,2409L
			,2801L
			,2632L
			,2769L
			,2846L
			,2781L
			,4923L
			,4818L
			,3077L
			,3630L
			,3357L
			,3869L
			,3527L
			,3570L
			,4024L
			,5041L
			,5154L
			,4935L
			,5784L
			,4330L
			,8804L
			,4612L
			,6373L
			,4794L
			,9741L
			,5178L
			,5846L
			,5401L
			,5550L
			,5627L
			,5858L
			,6434L
			,8151L
			,6604L
			,6927L
			,7226L
			,7097L
			,7900L
			,9729L
			,9178L
			,8942L
			,9731L
			,9124L
			,9406L
			,10162L
			,9790L
			,9972L
			,15252L
			,10728L
			,10805L
			,12450L
			,10951L
			,11028L
			,11177L
			,15078L
			,22422L
			,14827L
			,14504L
			,13531L
			,14997L
			,14323L
			,16039L
			,16842L
			,24005L
			,18909L
			,18066L
			,18530L
			,19568L
			,34956L
			,19762L
			,21000L
			,34820L
			,28528L
			,21533L
			,25274L
			,21979L
			,36360L
			,22205L
			,44567L
			,27854L
			,28035L
			,29150L
			,36596L
			,34105L
			,40735L
			,31165L
			,38821L
			,34908L
			,36975L
			,41547L
			,37634L
			,69885L
			,44184L
			,82201L
			,40762L
			,42533L
			,62855L
			,50240L
			,55889L
			,71504L
			,49833L
			,72421L
			,80167L
			,81542L
			,57004L
			,90568L
			,64058L
			,114069L
			,90975L
			,100073L
			,81818L
			,84741L
			,75670L
			,86717L
			,136957L
			,78396L
			,140808L
			,83295L
			,183341L
			,90595L
			,136056L
			,105722L
			,112893L
			,119947L
			,129425L
			,113891L
			,121062L
			,132674L
			,147572L
			,143721L
			,139728L
			,168991L
			,154066L
			,157488L
			,165113L
			,158965L
			,161691L
			,218615L
			,170012L
			,173890L
			,283628L
			,189017L
			,223269L
			,203488L
			,310079L
			,245450L
			,219613L
			,233955L
			,253736L
			,291074L
			,257612L
			,301638L
			,283449L
			,287300L
			,368601L
			,502243L
			,315757L
			,311554L
			,316453L
			,320656L
			,607527L
			,331703L
			,343902L
			,359029L
			,362907L
			,898601L
			,511348L
			,423101L
			,626589L
			,453568L
			,545509L
			,510687L
			,596862L
			,874255L
			,541061L
			,894827L
			,570749L
			,683563L
			,628007L
			,637109L
			,627311L
			,632210L
			,959014L
			,660355L
			,721936L
			,675605L
			,1258799L
			,970491L
			,816475L
			,786008L
			,1138694L
			,1361984L
			,1642577L
			,964255L
			,994629L
			,1051748L
			,1081436L
			,1111810L
			,1168372L
			,1169068L
			,2427171L
			,1231104L
			,1954380L
			,1688857L
			,1302916L
			,1259521L
			,1292565L
			,1335960L
			,1382291L
			,2370609L
			,2223054L
			,1602483L
			,1897818L
			,2413732L
			,1837756L
			,2045691L
			,1958884L
			,2016003L
			,2193246L
			,2046377L
			,3280109L
			,2249808L
			,2280182L
			,2400172L
			,4000757L
			,3213901L
			,2490625L
			,2562437L
			,2552086L
			,2595481L
			,2628525L
			,4433237L
			,2984774L
			,3852291L
			,4237928L
			,4389842L
			,3440239L
			,5264956L
			,4296185L
			,3796640L
			,3974887L
			,4005261L
			,4062380L
			,4845289L
			,4537002L
			,5114523L
			,4529990L
			,4680354L
			,4890797L
			,5042711L
			,5053062L
			,5086106L
			,5992325L
			,9027949L
			,7736424L
			,7445500L
			,7415126L
			,10167585L
			,7236879L
			,7502619L
			,7859020L
			,13564951L
			,12912082L
			,9723065L
			,7771527L
			,7980148L
			,8067641L
			,12468188L
			,9066992L
			,9210344L
			,16443075L
			,9420787L
			,21632592L
			,9933508L
			,10095773L
			,17582085L
			,13407451L
			,13229204L
			,14652005L
			,21296845L
			,16303871L
			,14739498L
			,16712963L
			,23949842L
			,17790706L
			,15630547L
			,17190492L
			,16047789L
			,24423223L
			,17192314L
			,18001149L
			,17134633L
			,21535180L
			,18487779L
			,22439548L
			,26636655L
			,33438504L
			,31874131L
			,20029281L
			,28059456L
			,27881209L
			,27968702L
			,29276993L
			,31043369L
			,31452461L
			,31678336L
			,32760752L
			,32343510L
			,32765180L
			,40927327L
			,32821039L
			,49899813L
			,34048938L
			,35680093L
			,34326947L
			,35622412L
			,66199256L
			,38517060L
			,74976265L
			,46665936L
			,47910490L
			,47997983L
			,48088737L
			,49306274L
			,55940665L
			,55849911L
			,57245695L
			,101821668L
			,116099069L
			,63130797L
			,64021846L
			,68383164L
			,67965922L
			,65586219L
			,66869977L
			,67147986L
			,94576426L
			,72844007L
			,95999227L
			,74139472L
			,122139921L
			,85182996L
			,86427550L
			,94663919L
			,161446403L
			,157762333L
			,96086720L
			,97395011L
			,234290410L
			,130000774L
			,132734205L
			,186161767L
			,259584001L
			,127152643L
			,246629399L
			,301438396L
			,132456196L
			,227032622L
			,134017963L
			,161811905L
			,139991993L
			,230104683L
			,146983479L
			,159322468L
			,395736813L
			,179846915L
			,182514270L
			,181091469L
			,221816562L
			,561022397L
			,193481731L
			,223239363L
			,367024615L
			,257153417L
			,274009956L
			,259886848L
			,266474159L
			,308795384L
			,261170606L
			,272448189L
			,279439675L
			,391916588L
			,362361185L
			,704532197L
			,299314461L
			,321083462L
			,306305947L
			,370222842L
			,339169383L
			,494264751L
			,467491687L
			,363605739L
			,497249319L
			,481703410L
			,669537303L
			,416721094L
			,480392780L
			,758941203L
			,517040265L
			,569965990L
			,521057454L
			,527644765L
			,1066893382L
			,533618795L
			,1064230741L
			,578754136L
			,827363401L
			,605620408L
			,620397923L
			,627389409L
			,645475330L
			,669911686L
			,702775122L
			,755890477L
			,780326833L
			,897113874L
			,1091023444L
			,898424504L
			,937778548L
			,933761359L
			,944365859L
			,1571755268L
			,1038097719L
			,1044685030L
			,1054676249L
			,1184374544L
			,1432043299L
			,1139239203L
			,1647140981L
			,1936522223L
			,1199152059L
			,1226018331L
			,1233009817L
			,1247787332L
			,1978446389L
			,1315387016L
			,2253165564L
			,1483101955L
			,1536217310L
			,1677440707L
			,1309761972L
			,1832185863L
			,2387026535L
			,2360072046L
			,2845979282L
			,2287686066L
			,2099361279L
			,2082782749L
			,2183924233L
			,2193915452L
			,3398169765L
			,2792863927L
			,2338391262L
			,2473805663L
			,2625148988L
			,2425170390L
			,4619085842L
			,3730132762L
			,2557549304L
			,5029903515L
			,2798488971L
			,3776801986L
			,3565884704L
			,3734932362L
			,4763561652L
			,5039894734L
			,8540363638L
			,4276698201L
			,4182144028L
			,4266706982L
			,5904275966L
			,4283285512L
			,4377839685L
			,4522315495L
			,4532306714L
			,4895940566L
			,4812196925L
			,6980632999L
			,4898976053L
			,4982719694L
			,9470160670L
			,5356038275L
			,6287682066L
			,6292481666L
			,6364373675L
			,7320804466L
			,7300817066L
			,7748028732L
			,8001639344L
			,8448851010L
			,11876573565L
			,8559983713L
			,8543405183L
			,8465429540L
			,8549992494L
			,8900155180L
			,8661125197L
			,12303524160L
			,9054622209L
			,17561280377L
			,9708137491L
			,11176570600L
			,14369136723L
			,9881695747L
			,10338757969L
			,17103388896L
			,16174177413L
			,14366013019L
			,15025498872L
			,14907778858L
			,14621621532L
			,15048845798L
			,15749668076L
			,16467068884L
			,16914280550L
			,24077274214L
			,17008834723L
			,17015422034L
			,17126554737L
			,17211117691L
			,19837695797L
			,17715747406L
			,18762759700L
			,29529400390L
			,19589833238L
			,20046895460L
			,20220453716L
			,24503317279L
			,24704770988L
			,27253038519L
			,31223023211L
			,28987634551L
			,29391511891L
			,38352592938L
			,29670467330L
			,30371289608L
			,30798513874L
			,32765090110L
			,44550212739L
			,44723770995L
			,56923505849L
			,36478507106L
			,34226539725L
			,34337672428L
			,36800950929L
			,51942287131L
			,37762642866L
			,52985543826L
			,47473492235L
			,51018967590L
			,40267349176L
			,58051552393L
			,49208088267L
			,51957809507L
			,56240673070L
			,69566041039L
			,90578345498L
			,82740801005L
			,69243597216L
			,60468981204L
			,61169803482L
			,83951999341L
			,66991629835L
			,94852503322L
			,68564212153L
			,107258979011L
			,70705046831L
			,71027490654L
			,71138623357L
			,74563593795L
			,135894286472L
			,86970731133L
			,87740841411L
			,91286316766L
			,89475437443L
			,92225158683L
			,107259640660L
			,101165897774L
			,108198482577L
			,129734015635L
			,121638784686L
			,129033193357L
			,139591702807L
			,132197294136L
			,127460611039L
			,132308426839L
			,135555841988L
			,137696676666L
			,139269258984L
			,139702835510L
			,160614060800L
			,179027158177L
			,210619193461L
			,222526573121L
			,161534324928L
			,176446168576L
			,174711572544L
			,195000482071L
			,180761754209L
			,181700596126L
			,199484799343L
			,222804682460L
			,228626508813L
			,229837267263L
			,253947211525L
			,261230487493L
			,300205763607L
			,259657905175L
			,259769037878L
			,263016453027L
			,267864268827L
			,310267414532L
			,276965935650L
			,334269741055L
			,342314656926L
			,398972741697L
			,356412168670L
			,336245897472L
			,430393380101L
			,438500260578L
			,358146764702L
			,404505278586L
			,362462350335L
			,380246553552L
			,381185395469L
			,452641949723L
			,513605116700L
			,766967628921L
			,483784478788L
			,539982388677L
			,658352528309L
			,794051893320L
			,578131683359L
			,610178925753L
			,1171472907507L
			,544830204477L
			,587233350182L
			,611235676705L
			,670515638527L
			,1123784042453L
			,692658066142L
			,694392662174L
			,698708247807L
			,1151218065382L
			,720609115037L
			,738393318254L
			,918110395286L
			,742708903887L
			,1148153024390L
			,833827345192L
			,936426428511L
			,997389595488L
			,1023766867465L
			,1352887829640L
			,1084812593154L
			,1122961887836L
			,1132063554659L
			,1243538452284L
			,1584622945670L
			,1156065881182L
			,1215345843004L
			,1435366970029L
			,1281751315232L
			,1679135332398L
			,1989893226374L
			,1554436460229L
			,1393100909981L
			,1463318018924L
			,1481102222141L
			,1459002433291L
			,2608446752985L
			,1576536249079L
			,2724640674425L
			,1857594212657L
			,2108579460619L
			,2240928047772L
			,2216876147813L
			,2146728755301L
			,3035538682370L
			,2366563908386L
			,3255671581477L
			,2288129435841L
			,2371411724186L
			,3501680370600L
			,2437817196414L
			,3830414157477L
			,2762853537373L
			,2674852225213L
			,2944420241065L
			,2852103343272L
			,2856418928905L
			,3338696434798L
			,2922320452215L
			,3793412396892L
			,3316596645948L
			,4960682803891L
			,5294236125319L
			,3966173673276L
			,5069049207516L
			,5930523806690L
			,5796523584337L
			,8862461604408L
			,5227830653091L
			,7398500000305L
			,4654693344227L
			,6268231353891L
			,4659541160027L
			,7467854043876L
			,6840376805398L
			,5597172677428L
			,5437705762586L
			,5774423795487L
			,5619272466278L
			,5708522272177L
			,6261016887013L
			,5778739381120L
			,6238917098163L
			,8754302408534L
			,11024354237428L
			,7282770319224L
			,10843473003003L
			,8620867017503L
			,8625714833303L
			,11487261653297L
			,9314234504254L
			,13176376316053L
			,9882523997318L
			,9887371813118L
			,10092399106813L
			,10363215616404L
			,10097246922613L
			,10256713837455L
			,11146228034763L
			,14218039694931L
			,14063420595889L
			,13543787206237L
			,11327794738455L
			,14334237105480L
			,11947439370340L
			,12017656479283L
			,16331316204976L
			,17246581850806L
			,21206688619407L
			,15903637336727L
			,15908485152527L
			,18503391014821L
			,20245739613722L
			,21420193845268L
			,30502453451177L
			,24431484028093L
			,31783409461672L
			,24871581944692L
			,20144085650573L
			,20189646029426L
			,20353960760068L
			,21243474957376L
			,23163884514046L
			,22474022773218L
			,33224345098690L
			,23275234108795L
			,23345451217738L
			,40335121364820L
			,34407028351548L
			,36098131181953L
			,37328678997795L
			,31812122489254L
			,33150219187533L
			,34411876167348L
			,36047722987300L
			,55087356598049L
			,92416035595844L
			,56343870795675L
			,73485340552353L
			,59802701771013L
			,44785444788161L
			,40333731679999L
			,41387560607949L
			,40498046410641L
			,57682262460343L
			,42827983533286L
			,43717497730594L
			,45637907287264L
			,45749256882013L
			,46620685326533L
			,55157573706992L
			,80833167775461L
			,73376401985095L
			,80156662531081L
			,117485341528876L
			,66223998656602L
			,67562095354881L
			,92369942208546L
			,70459599154648L
			,95655620117633L
			,80831778090640L
			,84215544141235L
			,81721292287948L
			,81885607018590L
			,83161715213285L
			,125438790018542L
	);

	@Test
	void part1() {
		for(int i=25; i<inputs.size(); i++){
			List<Long> previous5 = inputs.subList(i-25, i);
			Long currentNumber = inputs.get(i);
			if(!isSumOf2NumberInTheList(currentNumber, previous5)){
				log.info("answer part1: " + currentNumber);
				return;
			}
		}
		AssertionErrors.fail("Error");
	}

	@Test
	void part2() {
		long invalidNumber = 1309761972L;
		for(int i=0; i<inputs.size(); i++){
			Long firstValue = inputs.get(i);
			if(firstValue < invalidNumber) {
				long sum = firstValue;
				long min = firstValue;
				long max = firstValue;
				for(int j=i+1; j<inputs.size(); j++){
					Long nextValue = inputs.get(j);
					sum += nextValue;
					if(sum > invalidNumber) {
						break;
					}
					if(nextValue > max){
						max = nextValue;
					}
					if(nextValue < min){
						min = nextValue;
					}
					if(sum == invalidNumber){
						long answer = min + max;
						log.info("answer part2: " + answer);
						return;
					}
				}
			}
		}
		AssertionErrors.fail("Error");
	}

	private boolean isSumOf2NumberInTheList(long sum, List<Long> numbers) {
		for(Long number: numbers) {
			long remainder = sum - number;
			if(numbers.contains(remainder)){
				return true;
			}
		}
		return false;
	}
}