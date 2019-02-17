import React, {Component} from 'react'

export interface ErrorBoundaryState {
    hasError :boolean 
}

export interface ErrorBoundaryProps {

}

export default class ErrorBoundary extends Component<ErrorBoundaryProps, ErrorBoundaryState> {
    constructor(props: ErrorBoundaryProps) {
        super(props)

        this.state = {
            hasError: false
        }
    }
    
    componentDidCatch(error :any, info : any) {
        console.log("componentdidcatch", error, info);
        this.setState({hasError: true});
    } 

    render() {
        if (this.state.hasError) {
            return <h1>Something bad has happened! </h1>
        }

        return this.props.children;
    }

}