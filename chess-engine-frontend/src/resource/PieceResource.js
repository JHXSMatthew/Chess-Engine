import whiteBishop from './whiteBishop.svg'
import whiteKing from './whiteKing.svg'
import whiteKnight from './whiteKnight.svg'
import whitePawn from './whitePawn.svg'
import whiteQueen from './whiteQueen.svg'
import whiteRook from './whiteRook.svg'

import blackBishop from './blackBishop.svg'
import blackKing from './blackKing.svg'
import blackKnight from './blackKnight.svg'
import blackPawn from './blackPawn.svg'
import blackQueen from './blackQueen.svg'
import blackRook from './blackRook.svg'
import dummy from './dummy.svg'

import React from 'react'
import ReactSVG from 'react-svg'


const omap = (f,o) => {
  const t = {}
  for(let i in o ){
    t[reverseCase(i)] = f(o[i])
  }
  return t;
}

const reverseCase = (a) =>{
  if(a[0] == a[0].toUpperCase()){
    return a[0].toLowerCase()
  }else{
    return a[0].toUpperCase()
  }
}


export const PiecesSVG = omap((a)=> <img width="100%" src={a}></img>, 
{
  p: whitePawn,
  P: blackPawn,
  b: whiteBishop,
  B: blackBishop,
  n: whiteKnight,
  N: blackKnight,
  k: whiteKing,
  K: blackKing,
  q: whiteQueen,
  Q: blackQueen,
  r: whiteRook,
  R: blackRook,
  0: dummy
})