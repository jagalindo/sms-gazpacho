library(doBy)
library(ggplot2)
library(reshape2)
library(bibtex)

#setwd("~/Dropbox/Documentos/Trabajo/Research/Doctorado/Papers/Ongoing/jagalindo-is/bibtex/newBach/4.nowsbutvamos.nononjcrconf")
#setwd("C:/Users/jagal/BitTorrent Sync/Repositories/collaboration/jagalindo15-is/bibtex/5.done")
#read data

bib <- read.bib("total.bib")

#filter by first variable
intensive<-bib[grep("k-vis",bib$review,ignore.case = TRUE)]
multiproduct<-bib[grep("k-mmodel",bib$review,ignore.case = TRUE)]
configuration<-bib[grep("k-configuration",bib$review,ignore.case = TRUE)]
testing<-bib[grep("k-testing",bib$review,ignore.case = TRUE)]
languages<-bib[grep("k-modeling",bib$review,ignore.case = TRUE)]
reverse<-bib[grep("k-reverse",bib$review,ignore.case = TRUE)]

#plotting the count of the first dimmension
name1<-c("Beyond software \nproduct lines","Testing and\nevolution of\nvariability intensive\nsystems","Reverse engieniering\nfeature models","Multi-model\nvariability analysis","Variability and\nmodeling expresiveness","Product\nconfiguration and\nderivation")
value1<-c(length(intensive),length(testing),length(reverse),length(multiproduct),length(languages),length(configuration));
variabilityContext1 <- data.frame(name1,value1)
ggplot(variabilityContext1,aes(x=name1,y=value1))+ geom_bar(stat="identity")+ 
theme_bw()+ xlab("Variability Context Facet") + ylab("Number of papers") +
geom_text(aes(y=value1, ymax=value1, label=value1),position= position_dodge(width=0.9), vjust=-.5)+
  theme(axis.text=element_text(size=20), axis.title=element_text(size=16,face="bold"), axis.text.x = element_text(angle = 45, hjust = 1))

ggsave(file="VariabilityContextBar.pdf")

#filter by seoncd variable
tool<-bib[grep("k-tool",bib$review,ignore.case = TRUE)]
meth<-bib[grep("k-method",bib$review,ignore.case = TRUE)]
model<-bib[grep("k-rmodel",bib$review,ignore.case = TRUE)]
metric<-bib[grep("k-metric",bib$review,ignore.case = TRUE)]
proc<-bib[grep("k-process",bib$review,ignore.case = TRUE)]
dev.off
#plotting the count of the first dimmension
name2<-c("Metric","Tool","Model","Method","Process")
value2<-c(length(metric),length(tool),length(model),length(meth),length(proc));
variabilityContext2 <- data.frame(name2,value2)
ggplot(variabilityContext2,aes(x=name2,y=value2))+ geom_bar(stat="identity")+ 
  theme_bw()+ xlab("Contribution Facet") + ylab("Number of papers") +
  geom_text(aes(y=value2, ymax=value2, label=value2),position= position_dodge(width=0.9), vjust=-.5)+
  theme(axis.text=element_text(size=20), axis.title=element_text(size=16,face="bold"), axis.text.x = element_text(angle = 45, hjust = 1))

ggsave(file="ContributionBar.pdf")

#filter by third variable
experi<-bib[grep("k-experience",bib$review,ignore.case = TRUE)]
philo<-bib[grep("k-philosophical",bib$review,ignore.case = TRUE)]
opin<-bib[grep("k-opinion",bib$review,ignore.case = TRUE)]
solu<-bib[grep("k-solution",bib$review,ignore.case = TRUE)]
eval<-bib[grep("k-evaluation",bib$review,ignore.case = TRUE)]
vali<-bib[grep("k-validation",bib$review,ignore.case = TRUE)]

#plotting the count of the first dimmension
name3<-c("Evaluation\nresearch","Validation\nresearch","Solution\nproposal","Philosophical\npaper","Experience\nreport", "Opinion\npaper")
value3<-c(length(eval),length(vali),length(solu),length(philo),length(experi),length(opin));
variabilityContext3 <- data.frame(name3,value3)
ggplot(variabilityContext3,aes(x=name3,y=value3))+ geom_bar(stat="identity")+ 
  theme_bw()+ xlab("Research Facet") + ylab("Number of papers") +
  geom_text(aes(y=value3, ymax=value3, label=value3),position= position_dodge(width=0.9), vjust=-.5)+
  theme(axis.text=element_text(size=20), axis.title=element_text(size=16,face="bold"), axis.text.x = element_text(angle = 45, hjust = 1))

ggsave(file="ResearchBar.pdf")


#-----------






#count AND FIRST second variable
toolintensive<-length(intensive[grep("tool",intensive$review,ignore.case = TRUE)])
methodintensive<-length(intensive[grep("meth",intensive$review,ignore.case = TRUE)])
modelintensive<-length(intensive[grep("model",intensive$review,ignore.case = TRUE)])
metricintensive<-length(intensive[grep("metric",intensive$review,ignore.case = TRUE)])
openintensive<-length(intensive[grep("proc",intensive$review,ignore.case = TRUE)])

#---------
toolmultiproduct<-length(multiproduct[grep("tool",multiproduct$review,ignore.case = TRUE)])
methodmultiproduct<-length(multiproduct[grep("meth",multiproduct$review,ignore.case = TRUE)])
modelmultiproduct<-length(multiproduct[grep("model",multiproduct$review,ignore.case = TRUE)])
metricmultiproduct<-length(multiproduct[grep("metric",multiproduct$review,ignore.case = TRUE)])
openmultiproduct<-length(multiproduct[grep("proc",multiproduct$review,ignore.case = TRUE)])

#---------
toolconfiguration<-length(configuration[grep("tool",configuration$review,ignore.case = TRUE)])
methodconfiguration<-length(configuration[grep("meth",configuration$review,ignore.case = TRUE)])
modelconfiguration<-length(configuration[grep("model",configuration$review,ignore.case = TRUE)])
metricconfiguration<-length(configuration[grep("metric",configuration$review,ignore.case = TRUE)])
openconfiguration<-length(configuration[grep("proc",configuration$review,ignore.case = TRUE)])

#---------
tooltesting<-length(testing[grep("tool",testing$review,ignore.case = TRUE)])
methodtesting<-length(testing[grep("meth",testing$review,ignore.case = TRUE)])
modeltesting<-length(testing[grep("model",testing$review,ignore.case = TRUE)])
metrictesting<-length(testing[grep("metric",testing$review,ignore.case = TRUE)])
opentesting<-length(testing[grep("proc",testing$review,ignore.case = TRUE)])

#---------
toollanguages<-length(languages[grep("tool",languages$review,ignore.case = TRUE)])
methodlanguages<-length(languages[grep("meth",languages$review,ignore.case = TRUE)])
modellanguages<-length(languages[grep("model",languages$review,ignore.case = TRUE)])
metriclanguages<-length(languages[grep("metric",languages$review,ignore.case = TRUE)])
openlanguages<-length(languages[grep("proc",languages$review,ignore.case = TRUE)])

#---------
toolreverse<-length(reverse[grep("tool",reverse$review,ignore.case = TRUE)])
methodreverse<-length(reverse[grep("meth",reverse$review,ignore.case = TRUE)])
modelreverse<-length(reverse[grep("model",reverse$review,ignore.case = TRUE)])
metricreverse<-length(reverse[grep("metric",reverse$review,ignore.case = TRUE)])
openreverse<-length(reverse[grep("proc",reverse$review,ignore.case = TRUE)])


tool<-length(bib[grep("tool",bib$review,ignore.case = TRUE)])
method<-length(bib[grep("meth",bib$review,ignore.case = TRUE)])
model<-length(bib[grep("model",bib$review,ignore.case = TRUE)])
metric<-length(bib[grep("metric",bib$review,ignore.case = TRUE)])
process<-length(bib[grep("proc",bib$review,ignore.case = TRUE)])

#---------

#count the thrid variable
experienceintensive<-length(intensive[grep("experi",intensive$review,ignore.case = TRUE)])
philointensive<-length(intensive[grep("philo",intensive$review,ignore.case = TRUE)])
opinionintensive<-length(intensive[grep("opin",intensive$review,ignore.case = TRUE)])
solutionintensive<-length(intensive[grep("solu",intensive$review,ignore.case = TRUE)])
validationintensive<-length(intensive[grep("vali",intensive$review,ignore.case = TRUE)])
evaluationintensive<-length(intensive[grep("eval",intensive$review,ignore.case = TRUE)])
#-----------
experiencemultiproduct<-length(multiproduct[grep("experi",multiproduct$review,ignore.case = TRUE)])
philomultiproduct<-length(multiproduct[grep("philo",multiproduct$review,ignore.case = TRUE)])
opinionmultiproduct<-length(multiproduct[grep("opin",multiproduct$review,ignore.case = TRUE)])
solutionmultiproduct<-length(multiproduct[grep("solu",multiproduct$review,ignore.case = TRUE)])
validationmultiproduct<-length(multiproduct[grep("vali",multiproduct$review,ignore.case = TRUE)])
evaluationmultiproduct<-length(multiproduct[grep("eval",multiproduct$review,ignore.case = TRUE)])
#-----------
experienceconfiguration<-length(configuration[grep("experi",configuration$review,ignore.case = TRUE)])
philoconfiguration<-length(configuration[grep("philo",configuration$review,ignore.case = TRUE)])
opinionconfiguration<-length(configuration[grep("opin",configuration$review,ignore.case = TRUE)])
solutionconfiguration<-length(configuration[grep("solu",configuration$review,ignore.case = TRUE)])
validationconfiguration<-length(configuration[grep("vali",configuration$review,ignore.case = TRUE)])
evaluationconfiguration<-length(configuration[grep("eval",configuration$review,ignore.case = TRUE)])
#-----------
experiencetesting<-length(testing[grep("experi",testing$review,ignore.case = TRUE)])
philotesting<-length(testing[grep("philo",testing$review,ignore.case = TRUE)])
opiniontesting<-length(testing[grep("opin",testing$review,ignore.case = TRUE)])
solutiontesting<-length(testing[grep("solu",testing$review,ignore.case = TRUE)])
validationtesting<-length(testing[grep("vali",testing$review,ignore.case = TRUE)])
evaluationtesting<-length(testing[grep("eval",testing$review,ignore.case = TRUE)])
#-----------
experiencelanguages<-length(languages[grep("experi",languages$review,ignore.case = TRUE)])
philolanguages<-length(languages[grep("philo",languages$review,ignore.case = TRUE)])
opinionlanguages<-length(languages[grep("opin",languages$review,ignore.case = TRUE)])
solutionlanguages<-length(languages[grep("solu",languages$review,ignore.case = TRUE)])
validationlanguages<-length(languages[grep("vali",languages$review,ignore.case = TRUE)])
evaluationlanguages<-length(languages[grep("eval",languages$review,ignore.case = TRUE)])
#-----------
experiencereverse<-length(reverse[grep("experi",reverse$review,ignore.case = TRUE)])
philoreverse<-length(reverse[grep("philo",reverse$review,ignore.case = TRUE)])
opinionreverse<-length(reverse[grep("opin",reverse$review,ignore.case = TRUE)])
solutionreverse<-length(reverse[grep("solu",reverse$review,ignore.case = TRUE)])
validationreverse<-length(reverse[grep("vali",reverse$review,ignore.case = TRUE)])
evaluationreverse<-length(reverse[grep("eval",reverse$review,ignore.case = TRUE)])

experience<-length(bib[grep("experi",bib$review,ignore.case = TRUE)])
philo<-length(bib[grep("philo",bib$review,ignore.case = TRUE)])
opinion<-length(bib[grep("opin",bib$review,ignore.case = TRUE)])
solution<-length(bib[grep("solu",bib$review,ignore.case = TRUE)])
validation<-length(bib[grep("vali",bib$review,ignore.case = TRUE)])
evaluation<-length(bib[grep("eval",bib$review,ignore.case = TRUE)])
#-----------






#now we are going to modify the inproceding by adding the booktitle as a journal name
#unique
conferences<-bib[grep("InProceedings",bib$bibtype,ignore.case = TRUE)]$booktitle
journals<-bib[grep("article",bib$bibtype,ignore.case = TRUE)]$journal


count_unique_words <-function(wlist) {
  ucountlist = list()
  unamelist = c()
  for (i in wlist)
  {
    if (is.element(i, unamelist))
      ucountlist[[i]] <- ucountlist[[i]] +1
    else
    {
      listlen <- length(ucountlist)
      ucountlist[[i]] <- 1
      unamelist <- c(unamelist, i)
    }
  }
  ucountlist
}



cat("Conferences and Workshops with more than one publications\n")
expt_counts <- count_unique_words(conferences)
for(i in names(expt_counts)){
  if(expt_counts[[i]]>0){
  cat(i,"&", expt_counts[[i]],'\\\\', "\n")}
}
cat("\n")
cat("Journals\n")
expt_counts <- count_unique_words(journals)
for(i in names(expt_counts))
    cat(i,"&", expt_counts[[i]],'\\\\', "\n")
