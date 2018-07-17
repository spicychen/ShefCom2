{-ADT: Deque

SORTS: Entry, Bool

SYNTAX:
  create: Deque
  addFront: Entry->Deque->Deque
  addBack: Entry->Deque->Deque
  empty: Deque->Bool
  removeFront: Deque->Deque
  removeBack: Deque->Deque
  front: Deque->Entry
  back: Deque->Entry
  
-}

module Deque where

  data Deque = [] | [Entry]
  data Entry = A
 
  create :: Deque
  create = []
 
  addFront :: Entry->Deque->Deque
  addFront e dq = e:[]
  
  addBack:: Entry->Deque->Deque
  addBack e dq = [] ++ e
  
  empty:: Deque->Bool
  empty [] = True
  empty dq = False
  
  removeFront:: Deque->Deque
  removeFront dq = tail dq
  
  removeBack:: Deque->Deque
  removeBack dq = init dq
  
  front:: Deque->Entry
  front dq = head dq
  
  back:: Deque->Entry
  back dq = last dq
