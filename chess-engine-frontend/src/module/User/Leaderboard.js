import React from 'react'

import { connect } from 'react-redux';

import UUID from  'uuid/v1';

import ReactTable from "react-table";
import "react-table/react-table.css";

import { 
    actionUserLogin,
    actionUserLoginFail,
    actionLoadLeaderboard,
    actionGetLeaderboard
} from './UserReducer';



import { Link, Redirect } from 'react-router-dom';


import './Login.css';


class Leaderboard extends React.Component{
  
  componentWillMount(){
    this.props.loadLeaderboard()
  }


  render(){
    const info = this.props.info
    var data = []
    for (let i in info){
      data.push(info[i])
    }
    console.log(data)
    // https://react-table.js.org/#/story/readme
    return (
      <div className="container">
      <h1 className="h3 mb-3 font-weight-normal d-flex justify-content-center">Leaderboard</h1>
      <ReactTable
        data={data}
        columns={[
          {
            columns: [
              {
                Header: "#",
                filterable: false,
                maxWidth: 40,
                Cell: (row) => {
                  // console.log(row)
                  return (row.viewIndex+1)
                }
              },
              {
                Header: "User",
                accessor: "userName"
              },
              {
                Header: "Matches Played",
                accessor: "matchGamePlayed"
              },
              {
                Header: "Matches Won",
                accessor: "matchGameWin"
              },
              {
                Header: "Ranked Played",
                accessor: "rankGamePlayed"
              },
              {
                Header: "Ranked Won",
                accessor: "rankGameWin"
              },
              {
                Header: "MMR",
                accessor: "mmr"
              }
            ]}
          ]
        }
        defaultSorted={[
          {
            id: "mmr",
            desc: true
          }
        ]}
        defaultPageSize={10}
        className="-striped -highlight"
        />
      </div>
    )
  }
}

const mapStateToProps = (state) => {
  return {
    ...state.user
  }
};

const mapDispatchToProps = (dispatch) => {
  return {
      loadLeaderboard: () => dispatch(actionGetLeaderboard())
  };
};


export default connect(mapStateToProps, mapDispatchToProps)(Leaderboard)
