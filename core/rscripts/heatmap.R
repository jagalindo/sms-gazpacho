#!/usr/bin/env Rscript

library(ggplot2)
library(gridExtra)
library(ggthemes)

script.dir <- dirname(sys.frame(1)$ofile)
setwd(script.dir);
#------------- Read data ----------

data = read.csv("../data_marga/heatmap.csv", header = TRUE,sep=';')

map_facets<-c(
  "k-evaluation" = "Evaluation\nResearch", 
  "k-experience" = "Experience\nReport",
  "k-opinion" = "Opinion\nPaper",
  "k-philosophical" = "Philosophical\nPaper",
  "k-solution" = "Solution\nProposal",
  "k-validation" = "Validation\nResearch",
  "k-vis" = "Variability-intensive\n systems analysis",
  "k-testing" = "Testing and\nevolution",
  "k-reverse" = "Reverse\nengineering",
  "k-mmodel" = "Multi-model\nvariability analysis",
  "k-modeling" = "Variability \nmodelling",
  "k-configuration" = "Product configuration\nand derivation"
  )

#Order like in the rest of sections
#data$vf <- factor(data$vf, levels = rev(c("k-configuration","k-testing","k-reverse","k-mmodel","k-modeling","k-vis")))
#data$rf <- factor(data$rf, levels = c("k-opinion","k-philosophical","k-solution","k-evaluation","k-validation","k-experience"))

ggplot(data, aes(rf, vf)) + 
  geom_tile(aes(fill = count),colour = "white") + 
  geom_text(aes(label = count),size=5) +
  scale_fill_gradient(high="#707070", low="white",name = "Number of papers") +
  ylab("Variability context facet") + 
  xlab("Research facet")+ 
  theme(legend.position="right") +
#  scale_x_discrete(labels=map_facets)+
#  scale_y_discrete(labels=map_facets)+
  theme(axis.text=element_text(size=10), axis.title=element_text(size=11,face="bold"))+
  theme(panel.grid.major = element_blank(),
        panel.grid.minor = element_blank(), 
        panel.background = element_blank())+ theme(aspect.ratio=0.5)

#------------- Write to pdf ----------

ggsave("../data_marga/heatmap.pdf",  width = 10, height = 4)