(ns clugle.learn.wordnet.mappings)

;; mapping of filenumber to lexicographer file
(def lexnames
  {"00" "adj.all"
   "01" "adj.pert"
   "02" "adv.all"
   "03" "noun.Tops"
   "04" "noun.act"
   "05" "noun.animal"
   "06" "noun.artifact"
   "07" "noun.attribute"
   "08" "noun.body"
   "09" "noun.cognition"
   "10" "noun.communication"
   "11" "noun.event"
   "12" "noun.feeling"
   "13" "noun.food"
   "14" "noun.group"
   "15" "noun.location"
   "16" "noun.motive"
   "17" "noun.object"
   "18" "noun.person"
   "19" "noun.phenomenon"
   "20" "noun.plant"
   "21" "noun.possession"
   "22" "noun.process"
   "23" "noun.quantity"
   "24" "noun.relation"
   "25" "noun.shape"
   "26" "noun.state"
   "27" "noun.substance"
   "28" "noun.time"
   "29" "verb.body"
   "30" "verb.change"
   "31" "verb.cognition"
   "32" "verb.communication"
   "33" "verb.competition"
   "34" "verb.consumption"
   "35" "verb.contact"
   "36" "verb.creation"
   "37" "verb.emotion"
   "38" "verb.motion"
   "39" "verb.perception"
   "40" "verb.possession"
   "41" "verb.social"
   "42" "verb.stative"
   "43" "verb.weather"
   "44" "adj.ppl"})

;; maps a part of speech flag to
;; a symbol representing that part
;; of speech
(def POS_TYPE
  {"n" :noun
   "v" :verb
   "a" :adjective
   "s" :adjective-satelite
   "r" :adverb})

;; maps CoreNLP parts of speech to
;; wordnet parts of speech
(def CORE_TO_WORDNET
  {"JJ" "a"
   "JJR" "a"
   "JJS" "a" 
   "NN" "n"
   "NNS" "n"
   "NNP" "n"
   "NNPS" "n"
   "PRP" "n"
   "PRP$" "n"
   "RB" "r"
   "RBR" "r"
   "RBS" "r" 
   "VB" "v"
   "VBD" "v"
   "VBG" "v"
   "VBN" "v"
   "VBP" "v"
   "VBZ" "v"})